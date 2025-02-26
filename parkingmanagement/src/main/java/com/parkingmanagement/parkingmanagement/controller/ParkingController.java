package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.dto.ParkingDTO;
import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import com.parkingmanagement.parkingmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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
    public ResponseEntity<Parking> register(@Valid @RequestBody ParkingDTO parkingDTO) {
        if (!userService.existsById(UUID.fromString(parkingDTO.getUserCreatorId()))) {
            return ResponseEntity.badRequest().build();
        }

        Parking newParking = new Parking();
        BeanUtils.copyProperties(parkingDTO, newParking);

        Parking savedParking = parkingService.save(newParking);

        return ResponseEntity.ok(savedParking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        if (!parkingService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        parkingService.delete(id);

        return ResponseEntity.ok().build();
    }
}
