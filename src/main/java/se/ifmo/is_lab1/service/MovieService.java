package se.ifmo.is_lab1.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.is_lab1.dto.request.MovieRequest;
import se.ifmo.is_lab1.dto.request.UpdateMovieRequest;
import se.ifmo.is_lab1.dto.response.MovieResponse;
import se.ifmo.is_lab1.exceptions.*;
import se.ifmo.is_lab1.model.Movie;
import se.ifmo.is_lab1.model.Person;
import se.ifmo.is_lab1.model.User;
import se.ifmo.is_lab1.model.enums.MovieGenre;
import se.ifmo.is_lab1.model.enums.Role;
import se.ifmo.is_lab1.repository.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final PersonRepository personRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    public MovieResponse getMovie(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);
        return toMovieResponse(movie);
    }

    public Page<MovieResponse> getAllMovies(Pageable pageable,
                                            String movieName,
                                            String directorName) {
        Page<Movie> movies =
                movieRepository.findByFilter(
                        movieName, directorName, pageable
                );
        return movies.map(this::toMovieResponse);
    }

    public MovieResponse createMovie(MovieRequest movieRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);


        Movie requestObject = toMovie(movieRequest);
        if (movieRequest.getDirectorId() != null) {
            requestObject.setDirector(
                    personRepository.findById(movieRequest.getDirectorId())
                            .orElseThrow(PersonNotFoundException::new)
            );
        }
        if (movieRequest.getScreenwriterId() != null) {
            requestObject.setScreenwriter(
                    personRepository.findById(movieRequest.getScreenwriterId())
                            .orElseThrow(PersonNotFoundException::new)
            );
        }
        if (movieRequest.getOperatorId() != null) {
            requestObject.setOperator(
                    personRepository.findById(movieRequest.getOperatorId())
                            .orElseThrow(PersonNotFoundException::new)
            );
        }
        requestObject.setCoordinates(
                coordinatesRepository.findById(movieRequest.getCoordinatesId())
                        .orElseThrow(CoordinatesNotFoundException::new)
        );
        requestObject.setUser(user);
        Movie savedMovie = movieRepository.save(requestObject);
        return toMovieResponse(savedMovie);
    }

    public MovieResponse updateMovie(UpdateMovieRequest movieRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        Movie existingMovie = movieRepository.findById(movieRequest.getId())
                .orElseThrow(MovieNotFoundException::new);

        if (!(existingMovie.getIsEditable() && user.getRole().equals(Role.ADMIN)) ||
                !existingMovie
                        .getUser()
                        .getId()
                        .equals(user.getId())) {
            throw new NoAccessToObjectException();
        }
        existingMovie = toMovieFromExisting(movieRequest, existingMovie);
        existingMovie.setCoordinates(
                coordinatesRepository.findById(movieRequest.getCoordinatesId())
                        .orElseThrow(CoordinatesNotFoundException::new)
        );
        if (movieRequest.getDirectorId() != null) {
            existingMovie.setDirector(
                    personRepository.findById(movieRequest.getDirectorId())
                            .orElseThrow(PersonNotFoundException::new)
            );
        }
        if (movieRequest.getScreenwriterId() != null) {
            existingMovie.setScreenwriter(
                    personRepository.findById(movieRequest.getScreenwriterId())
                            .orElseThrow(PersonNotFoundException::new)
            );
        }
        if (movieRequest.getOperatorId() != null) {
            existingMovie.setOperator(
                    personRepository.findById(movieRequest.getOperatorId())
                            .orElseThrow(PersonNotFoundException::new)
            );
        }
        Movie response = movieRepository.save(existingMovie);
        return toMovieResponse(response);
    }

    @Transactional
    public MovieResponse deleteMovie(Integer objectId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!movieRepository.findById(objectId)
                .orElseThrow(MovieNotFoundException::new)
                .getUser()
                .getId()
                .equals(user.getId())) {
            throw new NoAccessToObjectException();
        }
        Movie movie = movieRepository.findById(objectId)
                .orElseThrow(MovieNotFoundException::new);

        if (movie.getDirector().getMovies().size() == 1 && movie.getCoordinates().getMovies().size() == 1
        && movie.getDirector().getLocation().getPersons().size() == 1) {
            movieRepository.deleteById(objectId);
            personRepository.deleteById(movie.getDirector().getId());
            locationRepository.deleteById(movie.getDirector().getLocation().getId());
            coordinatesRepository.deleteById(movie.getCoordinates().getId());
        } else {
            throw new MovieIsConnectedException();
        }
        return toMovieResponse(movie);
    }

    private MovieResponse toMovieResponse(Movie movie) {
        return MovieResponse
                .builder()
                .id(movie.getId())
                .name(movie.getName())
                .coordinates(movie.getCoordinates())
                .creationDate(movie.getCreationDate())
                .oscarsCount(movie.getOscarsCount())
                .budget(movie.getBudget())
                .totalBoxOffice(movie.getTotalBoxOffice())
                .mpaaRating(movie.getMpaaRating())
                .director(movie.getDirector())
                .screenwriter(movie.getScreenwriter())
                .operator(movie.getOperator())
                .length(movie.getLength())
                .goldenPalmCount(movie.getGoldenPalmCount())
                .usaBoxOffice(movie.getUsaBoxOffice())
                .tagline(movie.getTagline())
                .genre(movie.getGenre())
                .build();
    }

    private Movie toMovie(MovieRequest req) {
        Date now = new Date();
        return Movie.builder()
                .name(req.getName())
                .creationDate(now)
                .oscarsCount(req.getOscarsCount())
                .budget(req.getBudget())
                .totalBoxOffice(req.getTotalBoxOffice())
                .mpaaRating(req.getMpaaRating())
                .length(req.getLength())
                .goldenPalmCount(req.getGoldenPalmCount())
                .usaBoxOffice(req.getUsaBoxOffice())
                .tagline(req.getTagline())
                .genre(req.getGenre())
                .isEditable(req.getIsEditable())
                .build();
    }

    private Movie toMovieFromExisting(UpdateMovieRequest req, Movie movie) {
        return movie.toBuilder()
                .name(req.getName())
                .oscarsCount(req.getOscarsCount())
                .budget(req.getBudget())
                .totalBoxOffice(req.getTotalBoxOffice())
                .mpaaRating(req.getMpaaRating())
                .length(req.getLength())
                .goldenPalmCount(req.getGoldenPalmCount())
                .usaBoxOffice(req.getUsaBoxOffice())
                .tagline(req.getTagline())
                .genre(req.getGenre())
                .build();
    }

    public Double findAverageUsaBoxOffice() {
        return movieRepository.findAverageUsaBoxOffice();
    }

    public MovieResponse findMovieWithMaxDirector() {
        return toMovieResponse(movieRepository.findMovieWithMaxDirector());
    }

    public List<MovieResponse> findMoviesByTaglinePrefix(String prefix) {
        return movieRepository.findMoviesByTaglinePrefix(prefix)
                .stream()
                .map(this::toMovieResponse)
                .sorted()
                .toList();
    }

    public void redistributeOscars(MovieGenre sourceGenre, MovieGenre targetGenre) {
        Integer totalOscars = movieRepository.findTotalOscarsByGenre(sourceGenre);
        Long targetMoviesCnt = movieRepository.countMoviesByGenre(targetGenre);
        if (totalOscars != null && targetMoviesCnt > 0) {
            int newOscarCount = totalOscars / targetMoviesCnt.intValue();
            movieRepository.redistributeOscars(newOscarCount, targetGenre);
        }
    }

    public void addOscarsToLongMovies(int length) {
        movieRepository.addOscarsToLongMovies(length);
    }
}
