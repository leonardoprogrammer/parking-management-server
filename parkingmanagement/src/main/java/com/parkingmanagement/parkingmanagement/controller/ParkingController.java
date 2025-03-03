package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.dto.RequestParkingDTO;
import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import com.parkingmanagement.parkingmanagement.model.entity.User;
import com.parkingmanagement.parkingmanagement.security.SecurityUtils;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import com.parkingmanagement.parkingmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final UserService userService;

    public ParkingController(ParkingService parkingService, UserService userService) {
        this.parkingService = parkingService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parking> getById(@PathVariable UUID id) {
        return parkingService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Parking>> getByUserId(@PathVariable UUID userId) {
        List<Parking> parkings = new ArrayList<>();
        parkings.addAll(parkingService.findByUserCreatorId(userId));
        parkings.addAll(parkingService.findByEmployeeUserId(userId));

        // Remove objetos iguais
        Set<Parking> uniqueParkings = new HashSet<>(parkings);
        parkings.clear();
        parkings.addAll(uniqueParkings);

        return ResponseEntity.ok(parkings);
    }

    @PostMapping
    public ResponseEntity<Object> register(@Valid @RequestBody RequestParkingDTO requestParkingDTO) {
        UUID userCreatorid = UUID.fromString(requestParkingDTO.getUserCreatorId());

        if (!userService.existsById(userCreatorid)) {
            return ResponseEntity.badRequest().body("Não há usuário com este ID");
        }

        Parking newParking = new Parking(userCreatorid, requestParkingDTO.getName(), requestParkingDTO.getAddress());

        Parking savedParking = parkingService.save(newParking);

        return ResponseEntity.ok(savedParking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Parking parking = parkingService.findById(id).orElse(null);

        if (parking == null) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        User user = userService.findById(parking.getUserCreatorId()).orElse(null);
        if (!SecurityUtils.isCurrentUser(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        parkingService.delete(id);

        return ResponseEntity.ok().build();
    }
}
