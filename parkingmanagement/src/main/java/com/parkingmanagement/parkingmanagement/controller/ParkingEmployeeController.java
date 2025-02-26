package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.entity.ParkingEmployee;
import com.parkingmanagement.parkingmanagement.service.ParkingEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/parking-employees")
public class ParkingEmployeeController {

    @Autowired
    private ParkingEmployeeService parkingEmployeeService;

    @GetMapping("/{id}")
    public ResponseEntity<ParkingEmployee> getById(@PathVariable UUID id) {
        return parkingEmployeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<ParkingEmployee> getByParkingId(@PathVariable UUID parkingId) {
        return parkingEmployeeService.findByParkingId(parkingId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ParkingEmployee create(@RequestBody ParkingEmployee parkingEmployee) {
        return parkingEmployeeService.save(parkingEmployee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        parkingEmployeeService.deleteById(id);
    }
}
