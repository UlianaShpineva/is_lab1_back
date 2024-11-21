package se.ifmo.is_lab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.is_lab1.dto.response.UserResponse;
import se.ifmo.is_lab1.model.User;
import se.ifmo.is_lab1.model.enums.Role;
import se.ifmo.is_lab1.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void requestAdminRole() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        user.setAdminRoleRequested(true);
        userRepository.save(user);

    }

    @Transactional
    public UserResponse getUsernameInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .adminRoleRequested(user.isAdminRoleRequested())
                .build();
    }
}
