package se.ifmo.is_lab1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import se.ifmo.is_lab1.dto.request.LocationRequest;
import se.ifmo.is_lab1.dto.response.LocationResponse;
import se.ifmo.is_lab1.service.LocationService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/location")
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public List<LocationResponse> getAllLocations() {
        return locationService.getAllLocations();
    }

    @PostMapping
    public LocationResponse createLocation(@RequestBody @Valid LocationRequest locationRequest) {
        return locationService.createLocation(locationRequest);
    }
}
