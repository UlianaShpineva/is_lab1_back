package se.ifmo.is_lab1.exceptions;

public class CoordinatesNotFoundException extends RuntimeException {
    public CoordinatesNotFoundException() {
        super("Coordinates not found");
    }
}
