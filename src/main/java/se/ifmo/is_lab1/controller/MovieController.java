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
import se.ifmo.is_lab1.dto.response.MovieResponse;
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

    @GetMapping
    public Page<MovieResponse> getAllMovies(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String groupName,
            @RequestParam(required = false) String adminName
    ) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return movieService.getAllMovies(pageable, groupName, adminName);
    }

    @PostMapping
    public MovieResponse createMovie(@RequestBody @Valid MovieRequest movieRequest) {
        String username = getCurrentUser();
        return movieService.createMovie(movieRequest, username);
    }

    @DeleteMapping("/{id}")
    public MovieResponse deleteMovie(@PathVariable Integer id) {
        return movieService.deleteMovie(id);
    }

    @PutMapping
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
}