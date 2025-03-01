package com.parkingmanagement.parkedvehicles.controller;

import com.parkingmanagement.parkedvehicles.model.entity.ParkedVehicle;
import com.parkingmanagement.parkedvehicles.security.SecurityService;
import com.parkingmanagement.parkedvehicles.security.SecurityUtils;
import com.parkingmanagement.parkedvehicles.service.ParkedVehicleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parked-vehicle")
public class ParkedVehicleController {

    private final ParkedVehicleService parkedVehicleService;
    private final SecurityService securityService;

    public ParkedVehicleController(ParkedVehicleService parkedVehicleService, SecurityService securityService) {
        this.parkedVehicleService = parkedVehicleService;
        this.securityService = securityService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ParkedVehicle> getById(@PathVariable UUID id) {
        ParkedVehicle parkedVehicle = parkedVehicleService.findById(id).orElse(null);
        if (parkedVehicle == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkedVehicle.getParkingId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(parkedVehicle);
    }

    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<List<ParkedVehicle>> getParkedVehiclesByParkingId(@PathVariable UUID parkingId) {
        List<ParkedVehicle> parkedVehicles = parkedVehicleService.findParkedVehiclesByParkingId(parkingId);

        if (parkedVehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(parkedVehicles);
    }

    @GetMapping("/parking/{parkingId}/history")
    public Page<ParkedVehicle> getHistoryByParkingId(@PathVariable UUID parkingId, @RequestParam Integer page, @RequestParam Integer size) {
        return parkedVehicleService.getParkedVehiclesHistoryByParkingId(parkingId, page, size);
    }

    @GetMapping("/parking/{parkingId}/history/total")
    public Long getTotalHistoryByParkingId(@PathVariable UUID parkingId, @RequestParam Integer size) {
        return parkedVehicleService.getTotalPagesOfParkedVehiclesHistory(parkingId, size);
    }

    @PostMapping
    public ParkedVehicle add(@RequestBody ParkedVehicle parkedVehicle) {
        return parkedVehicleService.save(parkedVehicle);
    }
}
