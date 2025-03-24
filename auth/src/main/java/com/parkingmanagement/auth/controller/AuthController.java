package com.parkingmanagement.auth.controller;

import com.parkingmanagement.auth.model.dto.RequestLoginDTO;
import com.parkingmanagement.auth.model.dto.RequestRefreshTokenDTO;
import com.parkingmanagement.auth.security.JwtService;
import com.parkingmanagement.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Realiza login do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody RequestLoginDTO requestLoginDTO) {
        String email = requestLoginDTO.getEmail();
        String password = requestLoginDTO.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String accessToken = jwtService.authenticate(email, password);
        UUID userId = userService.getUserIdByEmail(email);
        UserDetails userDetails = userService.loadUserByUsername(email);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken, "userId", userId.toString()));
    }

    @Operation(summary = "Atualiza o token de acesso e o token de atualização")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens atualizados com sucesso")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@Valid @RequestBody RequestRefreshTokenDTO requestRefreshTokenDTO) {
        String refreshToken = requestRefreshTokenDTO.getRefreshToken();
        String newAccessToken = jwtService.refreshAccessToken(refreshToken);
        String newRefreshToken = jwtService.generateRefreshToken(userService.loadUserByUsername(jwtService.extractUsername(refreshToken)));
        return ResponseEntity.ok(Map.of("newAccessToken", newAccessToken, "newRefreshToken", newRefreshToken));
    }
}
