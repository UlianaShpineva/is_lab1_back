package se.ifmo.is_lab1.exceptions;

public class MovieIsConnectedException extends RuntimeException {
    public MovieIsConnectedException() {
        super("Movie is connected to other objects");
    }
}
