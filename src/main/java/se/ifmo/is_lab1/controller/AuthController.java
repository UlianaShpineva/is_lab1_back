package se.ifmo.is_lab1.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import se.ifmo.is_lab1.dto.request.AuthRequest;
import se.ifmo.is_lab1.dto.request.RegisterRequest;
import se.ifmo.is_lab1.dto.response.AuthResponse;
import se.ifmo.is_lab1.dto.response.RegisterResponse;
import se.ifmo.is_lab1.model.User;
import se.ifmo.is_lab1.service.AuthService;

import javax.swing.undo.AbstractUndoableEdit;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @SecurityRequirements
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    @SecurityRequirements
    public AuthResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping("/check_auth")
    public AuthResponse checkAuth(@AuthenticationPrincipal User user) {
        return authService.checkAuth(user);
    }
}
