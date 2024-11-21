package se.ifmo.is_lab1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
