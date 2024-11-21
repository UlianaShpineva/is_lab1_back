package se.ifmo.is_lab1.exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException() {
        super("Movie not found");
    }
}
