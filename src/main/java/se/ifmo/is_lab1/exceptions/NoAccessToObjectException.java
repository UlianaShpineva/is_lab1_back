package se.ifmo.is_lab1.exceptions;

public class NoAccessToObjectException extends RuntimeException {
    public NoAccessToObjectException() {
        super("No access to this object");
    }
}
