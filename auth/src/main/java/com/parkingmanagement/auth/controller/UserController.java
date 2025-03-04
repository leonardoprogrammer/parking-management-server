package com.parkingmanagement.auth.controller;

import com.parkingmanagement.auth.model.dto.ResponseSearchUserDTO;
import com.parkingmanagement.auth.model.entity.Parking;
import com.parkingmanagement.auth.model.entity.User;
import com.parkingmanagement.auth.security.SecurityUtils;
import com.parkingmanagement.auth.service.ParkingEmployeeService;
import com.parkingmanagement.auth.service.ParkingService;
import com.parkingmanagement.auth.service.UserService;
import com.parkingmanagement.auth.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ParkingService parkingService;
    private final ParkingEmployeeService parkingEmployeeService;

    public UserController(UserService userService, ParkingService parkingService, ParkingEmployeeService parkingEmployeeService) {
        this.userService = userService;
        this.parkingService = parkingService;
        this.parkingEmployeeService = parkingEmployeeService;
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
            String protectedEmail = Utils.maskEmail(user.getEmail());
            String protectCpf = Utils.maskCpf(user.getCpf());
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
}
