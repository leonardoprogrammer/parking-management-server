package com.parkingmanagement.parkedvehicles.controller;

import com.parkingmanagement.parkedvehicles.model.entity.ParkedVehicle;
import com.parkingmanagement.parkedvehicles.service.ParkedVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parked-vehicle")
public class ParkedVehicleController {

    @Autowired
    private ParkedVehicleService parkedVehicleService;

    @GetMapping("{id}")
    public ResponseEntity<ParkedVehicle> getById(@PathVariable UUID id) {
        return parkedVehicleService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<List<ParkedVehicle>> getParkedVehiclesByParkingId(@PathVariable UUID parkingId) {
        return parkedVehicleService.findParkedVehiclesByParkingId(parkingId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
    public ParkedVehicle create(@RequestBody ParkedVehicle parkedVehicle) {
        return parkedVehicleService.save(parkedVehicle);
    }
}
