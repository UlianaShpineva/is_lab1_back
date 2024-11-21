package se.ifmo.is_lab1.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException() {
        super("User already exists");
    }
}
