package se.ifmo.is_lab1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import se.ifmo.is_lab1.model.enums.Role;

@Data
@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private Role role;
    private boolean adminRoleRequested;
}