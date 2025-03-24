package com.parkingmanagement.auth.controller;

import com.parkingmanagement.auth.security.JwtService;
import com.parkingmanagement.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String accessToken = jwtService.authenticate(email, password);
        UUID userId = userService.getUserIdByEmail(email);
        UserDetails userDetails = userService.loadUserByUsername(email);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken, "userId", userId.toString()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        String newAccessToken = jwtService.refreshAccessToken(refreshToken);
        String newRefreshToken = jwtService.generateRefreshToken(userService.loadUserByUsername(jwtService.extractUsername(refreshToken)));
        return ResponseEntity.ok(Map.of("newAccessToken", newAccessToken, "newRefreshToken", newRefreshToken));
    }
}
