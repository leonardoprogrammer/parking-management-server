package com.parkingmanagement.auth.controller;

import com.parkingmanagement.auth.model.dto.UserDTO;
import com.parkingmanagement.auth.model.entity.User;
import com.parkingmanagement.auth.security.JwtService;
import com.parkingmanagement.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService, UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtService.authenticate(email, password);
        UUID userId = userService.getUserIdByEmail(email);

        return ResponseEntity.ok(Map.of("token", token, "userId", userId.toString()));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDTO userDTO) {
        if (userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        if (userService.existsByCpf(userDTO.getCpf())) {
            return ResponseEntity.badRequest().build();
        }

        User newUser = new User();
        BeanUtils.copyProperties(userDTO, newUser);
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userService.save(newUser);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/passwordEncoder")
    public ResponseEntity<String> passwordEncoderTest(@RequestBody String password) {
        return ResponseEntity.ok(passwordEncoder.encode(password));
    }
}
