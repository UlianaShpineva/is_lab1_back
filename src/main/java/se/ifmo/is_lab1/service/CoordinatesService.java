package se.ifmo.is_lab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.ifmo.is_lab1.dto.request.CoordinatesRequest;
import se.ifmo.is_lab1.dto.response.CoordinatesResponse;
import se.ifmo.is_lab1.model.Coordinates;
import se.ifmo.is_lab1.repository.CoordinatesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinatesService {
    private final CoordinatesRepository coordinatesRepository;

    public List<CoordinatesResponse> getAllCoordinates() {
        List<Coordinates> coordinates = coordinatesRepository.findAll();
        return coordinates
                .stream()
                .map(this::toCoordinatesResponse)
                .sorted()
                .toList();
    }

    public CoordinatesResponse createCoordinates(CoordinatesRequest coordinatesRequest) {
        Coordinates coordinates = toCoordinates(coordinatesRequest);
        coordinates = coordinatesRepository.save(coordinates);
        return toCoordinatesResponse(coordinates);
    }

    private CoordinatesResponse toCoordinatesResponse(Coordinates coordinates) {
        return CoordinatesResponse
                .builder()
                .id(coordinates.getId())
                .x(coordinates.getX())
                .y(coordinates.getY())
                .build();
    }

    private Coordinates toCoordinates(CoordinatesRequest req) {
        return Coordinates
                .builder()
                .x(req.getX())
                .y(req.getY())
                .build();
    }
}
