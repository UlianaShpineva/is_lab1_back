package se.ifmo.is_lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.ifmo.is_lab1.dto.response.UserResponse;
import se.ifmo.is_lab1.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class RegisterController {
    private final UserService userService;

    @PostMapping("/request-admin-role")
    public ResponseEntity<String> requestAdminRole() {
        userService.requestAdminRole();
        return new ResponseEntity<>("Admin role request submitted successfully.", HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserInfo() {
        return ResponseEntity.ok(userService.getUsernameInfo());
    }
}
