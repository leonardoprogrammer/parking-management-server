package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.dto.RequestRegisterParkingDTO;
import com.parkingmanagement.parkingmanagement.model.dto.RequestUpdateParkingDTO;
import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import com.parkingmanagement.parkingmanagement.security.SecurityService;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import com.parkingmanagement.parkingmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final UserService userService;
    private final SecurityService securityService;

    public ParkingController(ParkingService parkingService, UserService userService, SecurityService securityService) {
        this.parkingService = parkingService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/{parkingId}")
    public ResponseEntity<Object> getById(@PathVariable UUID parkingId) {
        Parking parking = parkingService.findById(parkingId).orElse(null);
        if (parking == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.currentUserIsOwnerOrEmployee(parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(parking);
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
    public ResponseEntity<Object> register(@Valid @RequestBody RequestRegisterParkingDTO requestRegisterParkingDTO) {
        UUID userCreatorid = UUID.fromString(requestRegisterParkingDTO.getUserCreatorId());

        if (!userService.existsById(userCreatorid)) {
            return ResponseEntity.badRequest().body("Não há usuário com este ID");
        }

        Parking newParking = new Parking(userCreatorid, requestRegisterParkingDTO.getName(), requestRegisterParkingDTO.getAddress());

        Parking savedParking = parkingService.save(newParking);

        return ResponseEntity.ok(savedParking);
    }

    @PutMapping("/{parkingId}")
    public ResponseEntity<Object> update(@PathVariable UUID parkingId, @Valid @RequestBody RequestUpdateParkingDTO requestUpdateParkingDTO) {
        Parking parking = parkingService.findById(parkingId).orElse(null);
        if (parking == null) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        if (!securityService.currentUserIsOwner(parkingId) && !securityService.currentUserIsEmployeeAndCanEditParking(parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        parking.setName(requestUpdateParkingDTO.getParkingName());
        parking.setAddress(requestUpdateParkingDTO.getParkingAddress());
        parking.setUpdatedAt(LocalDateTime.now());
        parkingService.save(parking);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{parkingId}")
    public ResponseEntity<Object> delete(@PathVariable UUID parkingId) {
        Parking parking = parkingService.findById(parkingId).orElse(null);

        if (parking == null) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        if (!securityService.currentUserIsOwner(parkingId) && !securityService.currentUserIsEmployeeAndCanEditParking(parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        parkingService.delete(parkingId);

        return ResponseEntity.ok().build();
    }
}
