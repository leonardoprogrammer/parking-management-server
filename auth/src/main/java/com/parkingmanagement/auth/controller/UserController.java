package com.parkingmanagement.auth.controller;

import com.parkingmanagement.auth.model.dto.*;
import com.parkingmanagement.auth.model.entity.Parking;
import com.parkingmanagement.auth.model.entity.User;
import com.parkingmanagement.auth.security.JwtService;
import com.parkingmanagement.auth.security.SecurityUtils;
import com.parkingmanagement.auth.service.ParkingEmployeeService;
import com.parkingmanagement.auth.service.ParkingService;
import com.parkingmanagement.auth.service.UserService;
import com.parkingmanagement.auth.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ParkingService parkingService;
    private final ParkingEmployeeService parkingEmployeeService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserController(UserService userService, ParkingService parkingService, ParkingEmployeeService parkingEmployeeService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.parkingService = parkingService;
        this.parkingEmployeeService = parkingEmployeeService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Obtém informações do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informações do usuário obtidas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/currentUser")
    public ResponseEntity<Object> getCurrentUser() {
        User user = userService.findByEmail(SecurityUtils.getCurrentUserEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        ResponseUserDTO responseUserDTO = new ResponseUserDTO(
                user.getId(),
                user.getName(),
                Utils.maskCpf(user.getCpf()),
                user.getEmail(),
                Utils.maskPhone(user.getTelephone()),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );

        return ResponseEntity.ok(responseUserDTO);
    }

    @Operation(summary = "Busca usuários por parâmetros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Informe pelo menos um parâmetro de busca"),
            @ApiResponse(responseCode = "404", description = "Usuários não encontrados")
    })
    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam UUID parkingId, @RequestParam String name, @RequestParam String email, @RequestParam String cpf) {
        if ((name == null || name.isBlank()) && (email == null || email.isBlank()) && (cpf == null || cpf.isBlank())) {
            return ResponseEntity.badRequest().body("Informe pelo menos um parâmetro de busca");
        }

        Parking parking = parkingService.findById(parkingId).orElse(null);
        if (parking == null) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        List<User> users = userService.searchUsers(name, email, cpf);

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<User> usersRemoved = new ArrayList<>();

        for (User user : users) {
            if (user.getEmail().equals(SecurityUtils.getCurrentUserEmail()) || parking.getUserCreatorId().equals(user.getId())) {
                usersRemoved.add(user);
            } else if (parkingEmployeeService.existsByParkingIdAndUserId(parkingId, user.getId())) {
                usersRemoved.add(user);
            }
        }

        users.removeAll(usersRemoved);

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ResponseSearchUserDTO> responseSearchUserDTOS = new ArrayList<>();
        for (User user : users) {
            String protectedEmail = Utils.maskProtectedEmail(user.getEmail());
            String protectCpf = Utils.maskProtectedCpf(user.getCpf());
            ResponseSearchUserDTO responseSearchUserDTO = new ResponseSearchUserDTO(
                    user.getId(),
                    user.getName(),
                    protectedEmail,
                    protectCpf
            );
            responseSearchUserDTOS.add(responseSearchUserDTO);
        }

        return ResponseEntity.ok(responseSearchUserDTOS);
    }

    @Operation(summary = "Registra um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "E-mail ou CPF já cadastrados")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RequestRegisterUserDTO requestRegisterUserDTO) {
        if (userService.existsByEmail(requestRegisterUserDTO.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        if (userService.existsByCpf(requestRegisterUserDTO.getCpf())) {
            return ResponseEntity.badRequest().build();
        }

        User newUser = new User();
        BeanUtils.copyProperties(requestRegisterUserDTO, newUser);
        newUser.setPassword(passwordEncoder.encode(requestRegisterUserDTO.getPassword()));

        User savedUser = userService.save(newUser);

        return ResponseEntity.ok(savedUser);
    }

    @Operation(summary = "Atualiza o usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/currentUser")
    public ResponseEntity<Object> updateCurrentUser(@Valid @RequestBody RequestUpdateUserDTO requestUpdateUserDTO) {
        User user = userService.findByEmail(SecurityUtils.getCurrentUserEmail()).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setName(requestUpdateUserDTO.getName());
        user.setTelephone(requestUpdateUserDTO.getTelephone());
        user.setUpdatedAt(LocalDateTime.now());
        userService.save(user);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Atualiza a senha do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "406", description = "Senha atual inválida")
    })
    @PutMapping("/changeCurrentUserPassword")
    public ResponseEntity<Object> changeCurrentUserPassword(@Valid @RequestBody RequestChangePasswordDTO requestChangePasswordDTO) {
        User user = userService.findByEmail(SecurityUtils.getCurrentUserEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (!passwordEncoder.matches(requestChangePasswordDTO.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        user.setPassword(passwordEncoder.encode(requestChangePasswordDTO.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userService.save(user);

        String newAccessToken = jwtService.generateToken(userService.loadUserByUsername(savedUser.getEmail()));
        String newRefreshToken = jwtService.generateRefreshToken(userService.loadUserByUsername(savedUser.getEmail()));

        return ResponseEntity.ok(Map.of("newAccessToken", newAccessToken, "newRefreshToken", newRefreshToken));
    }

    @Operation(summary = "Atualiza o e-mail do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "E-mail atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "406", description = "E-mail atual inválido"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    @PutMapping("/changeCurrentUserEmail")
    public ResponseEntity<Object> changeCurrentUserEmail(@Valid @RequestBody RequestChangeEmailDTO requestChangeEmailDTO) {
        User user = userService.findByEmail(SecurityUtils.getCurrentUserEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (requestChangeEmailDTO.getNewEmail().equals(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        if (userService.existsByEmail(requestChangeEmailDTO.getNewEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        user.setEmail(requestChangeEmailDTO.getNewEmail());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userService.save(user);

        String newAccessToken = jwtService.generateToken(userService.loadUserByUsername(savedUser.getEmail()));
        String newRefreshToken = jwtService.generateRefreshToken(userService.loadUserByUsername(savedUser.getEmail()));

        return ResponseEntity.ok(Map.of("newAccessToken", newAccessToken, "newRefreshToken", newRefreshToken));
    }
}
