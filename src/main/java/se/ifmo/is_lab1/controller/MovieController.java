package se.ifmo.is_lab1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.ifmo.is_lab1.dto.request.MovieRequest;
import se.ifmo.is_lab1.dto.request.UpdateMovieRequest;
import se.ifmo.is_lab1.dto.response.HistoryResponse;
import se.ifmo.is_lab1.dto.response.MovieResponse;
import se.ifmo.is_lab1.model.History;
import se.ifmo.is_lab1.model.enums.MovieGenre;
import se.ifmo.is_lab1.service.MovieService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/{id}")
    public MovieResponse getMovies(@PathVariable Integer id) {
        return movieService.getMovie(id);
    }

    @GetMapping("/all_movies")
    public List<MovieResponse> getAllMovies(){
        return movieService.getAllMovies();
    }

    @PostMapping("/create")
    public MovieResponse createMovie(@RequestBody @Valid MovieRequest movieRequest) {
        String username = getCurrentUser();
        return movieService.createMovie(movieRequest, username);
    }

    @PostMapping("/delete/{id}")
    public MovieResponse deleteMovie(@PathVariable Integer id) {
        return movieService.deleteMovie(id);
    }

    @PutMapping("/update")
    public MovieResponse updateMovie(@RequestBody @Valid UpdateMovieRequest movieRequest) {
        String username = getCurrentUser();
        return movieService.updateMovie(movieRequest, username);
    }

    @GetMapping("/average")
    public Double findAverageUsaBoxOffice() {
        return movieService.findAverageUsaBoxOffice();
    }

    @GetMapping("/max-director")
    public MovieResponse findMovieWithMaxDirector() {
        return movieService.findMovieWithMaxDirector();
    }

    @GetMapping("/tagline/{prefix}")
    public List<MovieResponse> findMoviesByTaglinePrefix(@PathVariable String prefix) {
        return movieService.findMoviesByTaglinePrefix(prefix);
    }

    @GetMapping("/redistribute-oscars/{source}/{target}")
    public void redistributeOscars(@PathVariable MovieGenre source, @PathVariable MovieGenre target) {
        movieService.redistributeOscars(source, target);
    }

    @GetMapping("/add-oscars/{length}")
    public void addOscarsToLongMovies(@PathVariable int length) {
        movieService.addOscarsToLongMovies(length);
    }

    private String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null || !(auth.getPrincipal() instanceof UserDetails)) {
            throw new IllegalStateException("no authentication");
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userDetails.getUsername();
    }

    @GetMapping("/history")
    public List<HistoryResponse> getMovieHistory() {
        return movieService.getHistory();
    }
}
