package se.ifmo.is_lab1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import se.ifmo.is_lab1.dto.request.CoordinatesRequest;
import se.ifmo.is_lab1.dto.response.CoordinatesResponse;
import se.ifmo.is_lab1.service.CoordinatesService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/coordinates")
public class CoordinatesController {
    private final CoordinatesService coordinatesService;

    @GetMapping
    public List<CoordinatesResponse> getAllCoordinates() {
        return coordinatesService.getAllCoordinates();
    }

    @PostMapping
    public CoordinatesResponse createCoordinates(@RequestBody @Valid CoordinatesRequest coordinatesRequest) {
        return coordinatesService.createCoordinates(coordinatesRequest);
    }
}
