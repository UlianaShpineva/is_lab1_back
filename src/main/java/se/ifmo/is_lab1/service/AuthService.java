package se.ifmo.is_lab1.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.ifmo.is_lab1.dto.request.AuthRequest;
import se.ifmo.is_lab1.dto.request.RegisterRequest;
import se.ifmo.is_lab1.dto.response.AuthResponse;
import se.ifmo.is_lab1.dto.response.RegisterResponse;
import se.ifmo.is_lab1.exceptions.UserAlreadyExistException;
import se.ifmo.is_lab1.exceptions.UserNotFoundException;
import se.ifmo.is_lab1.model.User;
import se.ifmo.is_lab1.model.enums.Role;
import se.ifmo.is_lab1.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername()))
            throw new UserAlreadyExistException();

        User user = User
                .builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        if (userRepository.findAll().isEmpty()) {
            user.setRole(Role.ADMIN);
        }
        user = userRepository.save(user);

//        String token = jwtUtils.generateJwtToken(user.getUsername());
        return new RegisterResponse(
                user.getUsername(),
                user.getRole()
        );
    }

    public AuthResponse login(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(UserNotFoundException::new);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        String token = jwtUtils.generateJwtToken(user.getUsername());
        return new AuthResponse(
                token
        );
    }
}
