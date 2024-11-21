package se.ifmo.is_lab1.exceptions;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException() {
        super("Location not found");
    }
}
