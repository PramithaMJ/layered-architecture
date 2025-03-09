package com.pmj.template.controller;

import com.pmj.template.dto.request.LoginRequest;
import com.pmj.template.dto.request.SignupRequest;
import com.pmj.template.dto.response.JwtAuthResponse;
import com.pmj.template.dto.response.UserResponse;
import com.pmj.template.entity.User;
import com.pmj.template.repository.UserRepository;
import com.pmj.template.security.JwtTokenProvider;
import com.pmj.template.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtAuthResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(ApiResponse.success("Login successful", new JwtAuthResponse(jwt)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        if (userService.existsByEmail(signupRequest.getEmail())) {
            throw new BadRequestException("Email is already in use!");
        }

        // Create user's account
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .fullName(signupRequest.getFullName())
                .roles(roles)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);

        UserResponse userResponse = UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .roles(savedUser.getRoles())
                .enabled(savedUser.isEnabled())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(savedUser.getUsername()).toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.success("User registered successfully", userResponse));
    }
}