package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @GetMapping("/{id}")
    public ResponseEntity<Parking> getById(@PathVariable UUID id) {
        return parkingService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Parking create(@RequestBody Parking parking) {
        return parkingService.save(parking);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        parkingService.delete(id);
    }
}
