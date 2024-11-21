package se.ifmo.is_lab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ifmo.is_lab1.model.User;
import se.ifmo.is_lab1.model.enums.Role;
import se.ifmo.is_lab1.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    @Transactional
    public List<User> getAdminRequests() {
        return userRepository.findByAdminRoleRequestedTrue();
    }

    @Transactional
    public void approveAdminRoleRequest(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        if (!user.isAdminRoleRequested()) {
            throw new IllegalArgumentException("No admin role request found for user ID: " + userId);
        }
        user.setRole(Role.ADMIN);
        user.setAdminRoleRequested(false);
        userRepository.save(user);
    }
}
