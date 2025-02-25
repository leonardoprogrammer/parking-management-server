package com.parkingmanagement.auth.controller;

import com.parkingmanagement.auth.model.entity.User;
import com.parkingmanagement.auth.security.SecurityUtils;
import com.parkingmanagement.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        User user = userService.findById(id)
                            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + id));

        if (!SecurityUtils.isCurrentUser(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(user);
    }
}
