package com.parkingmanagement.parkinglot.controller;

import com.parkingmanagement.parkinglot.entity.ParkingLot;
import com.parkingmanagement.parkinglot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @PostMapping
    public ResponseEntity<ParkingLot> createParkingLot(@RequestBody ParkingLot parkingLot) {
        ParkingLot createdParkingLot = parkingLotService.createParkingLot(parkingLot);
        return new ResponseEntity<>(createdParkingLot, HttpStatus.CREATED);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ParkingLot>> getParkingLotsByOwner(@PathVariable Long ownerId) {
        List<ParkingLot> parkingLots = parkingLotService.getParkingLotsByOwnerId(ownerId);
        return new ResponseEntity<>(parkingLots, HttpStatus.OK);
    }
}