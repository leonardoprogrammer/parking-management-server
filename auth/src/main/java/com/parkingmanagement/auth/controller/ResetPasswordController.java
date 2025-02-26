package com.parkingmanagement.auth.controller;

import com.parkingmanagement.auth.model.dto.ResetPasswordDTO;
import com.parkingmanagement.auth.model.entity.ResetPassword;
import com.parkingmanagement.auth.model.entity.User;
import com.parkingmanagement.auth.service.EmailService;
import com.parkingmanagement.auth.service.ResetPasswordService;
import com.parkingmanagement.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailservice;

    public ResetPasswordController(ResetPasswordService resetPasswordService, UserService userService, PasswordEncoder passwordEncoder, EmailService emailservice) {
        this.resetPasswordService = resetPasswordService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailservice = emailservice;
    }

    @PostMapping("/request")
    public ResponseEntity<String> requestResetPassword(@RequestParam String email) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma conta encontrada com este e-mail"));

        ResetPassword newResetPassword = new ResetPassword(user.getId(), user.getEmail());
        ResetPassword savedResetPassword = resetPasswordService.save(newResetPassword);

        String resetLink = "http://localhost:4200/reset-password?email=" + email + "&id=" + savedResetPassword.getId();

        emailservice.sendResetPasswordEmail(email, resetLink);

        savedResetPassword.setSentEmail(true);
        savedResetPassword.setUpdatedAt(LocalDateTime.now());
        resetPasswordService.save(savedResetPassword);

        return ResponseEntity.ok("Email enviado com link de redefinição de senha");
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmResetPasswordRequest(@RequestParam String email, @RequestParam UUID id) {
        ResetPassword resetPassword = resetPasswordService.findByIdAndEmail(id, email).orElse(null);

        if (resetPassword == null) {
            return ResponseEntity.badRequest().body("Solicitação de redefinição de senha não encontrada");
        }
        if (resetPassword.isReset()) {
            return ResponseEntity.badRequest().body("Solicitação de redefinição de senha já utilizada");
        }
        if (resetPassword.getDateExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Solicitação de redefinição de senha expirada");
        }

        return ResponseEntity.ok("Solicitação de redefinição de senha válida");
    }

    @PostMapping
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        ResetPassword resetPassword = resetPasswordService.findByIdAndEmail(UUID.fromString(resetPasswordDTO.getId()), resetPasswordDTO.getEmail()).orElse(null);

        if (resetPassword == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitação de redefinição de senha não encontrada");
        }
        if (resetPassword.isReset()) {
            return ResponseEntity.badRequest().body("Solicitação de redefinição de senha já utilizada");
        }
        if (resetPassword.getDateExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Solicitação de redefinição de senha expirada");
        }

        User user = userService.findByEmail(resetPasswordDTO.getEmail()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userService.save(user);

        resetPassword.setReset(true);
        resetPassword.setDateReset(LocalDateTime.now());
        resetPassword.setUpdatedAt(LocalDateTime.now());
        resetPasswordService.save(resetPassword);

        return ResponseEntity.ok("Senha redefinida com sucesso");
    }
}
