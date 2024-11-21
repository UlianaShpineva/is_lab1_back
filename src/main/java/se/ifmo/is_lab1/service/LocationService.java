package se.ifmo.is_lab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.ifmo.is_lab1.dto.request.LocationRequest;
import se.ifmo.is_lab1.dto.response.LocationResponse;
import se.ifmo.is_lab1.model.Location;
import se.ifmo.is_lab1.repository.LocationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<LocationResponse> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations
                .stream()
                .map(this::toLocationResponse)
                .sorted()
                .toList();
    }

    public LocationResponse createLocation(LocationRequest locationRequest) {
        Location location = toLocation(locationRequest);
        location = locationRepository.save(location);
        return toLocationResponse(location);
    }

    private LocationResponse toLocationResponse(Location location) {
        return LocationResponse
                .builder()
                .id(location.getId())
                .x(location.getX())
                .y(location.getY())
                .z(location.getX())
                .name(location.getName())
                .build();
    }

    private Location toLocation(LocationRequest req) {
        return Location
                .builder()
                .x(req.getX())
                .y(req.getY())
                .z(req.getZ())
                .name(req.getName())
                .build();
    }
}
