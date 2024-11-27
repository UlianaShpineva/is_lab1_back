package se.ifmo.is_lab1.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.ifmo.is_lab1.exceptions.NoAccessToObjectException;
import se.ifmo.is_lab1.exceptions.UserAlreadyExistException;
import se.ifmo.is_lab1.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({NoAccessToObjectException.class, UserAlreadyExistException.class, UserNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("error", "Forbidden");
        response.put("message", ex.getMessage());
        response.put("path", "/api/movie/update"); // или динамически передать текущий путь

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Другие обработчики исключений можно добавить по мере необходимости
}