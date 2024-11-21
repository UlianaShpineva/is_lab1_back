package se.ifmo.is_lab1.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username cannot be null or empty")
    private String username;
    @NotBlank(message = "Password cannot be null or empty")
    private String password;
}
