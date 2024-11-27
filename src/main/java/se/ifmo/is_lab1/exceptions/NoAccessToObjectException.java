package se.ifmo.is_lab1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "No access to this object")
public class NoAccessToObjectException extends RuntimeException {
    public NoAccessToObjectException() {
        super("No access to this object");
    }
}
