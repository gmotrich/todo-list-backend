package com.todo.api.service;

import com.todo.api.config.JwtService;
import com.todo.api.exception.UserException;
import com.todo.api.model.User;
import com.todo.api.model.request.AuthRequest;
import com.todo.api.model.response.AuthResponse;
import com.todo.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(AuthRequest request) {

        if (Objects.equals(request.getUsername(), "") || Objects.equals(request.getPassword(), "")) {
            throw new IllegalArgumentException("Username or password is blank");
        }

        var existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new UserException();
        }

        if (request.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        log.info("User - {} has successfully registered ", request.getUsername());
        return AuthResponse.builder()
                .token(jwtToken)
                .id(user.getId().toString())
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        if (Objects.equals(request.getUsername(), "") || Objects.equals(request.getPassword(), "")) {
            throw new IllegalArgumentException("Username or password is blank");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        log.info("User - {} successfully logged in", request.getUsername());
        return AuthResponse.builder()
                .token(jwtToken)
                .id(user.getId().toString())
                .build();
    }
}
