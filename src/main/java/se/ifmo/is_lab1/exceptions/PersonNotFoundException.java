package se.ifmo.is_lab1.exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super("Person not found");
    }
}
