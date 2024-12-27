package com.parkingmanagement.parkingspace.controller;

import com.parkingmanagement.parkingspace.entity.ParkingSpace;
import com.parkingmanagement.parkingspace.service.ParkingSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spaces")
public class ParkingSpaceController {

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @PostMapping
    public ResponseEntity<ParkingSpace> createParkingSpace(@RequestBody ParkingSpace parkingSpace) {
        ParkingSpace createdParkingSpace = parkingSpaceService.createParkingSpace(parkingSpace);
        return new ResponseEntity<>(createdParkingSpace, HttpStatus.CREATED);
    }

    @GetMapping("/parking-lot/{parkingLotId}")
    public ResponseEntity<List<ParkingSpace>> getParkingSpacsByParkingLotId(@PathVariable Long parkingLotId) {
        List<ParkingSpace> parkingSpaces = parkingSpaceService.getParkingSpacesByParkingLotId(parkingLotId);
        return new ResponseEntity<>(parkingSpaces, HttpStatus.OK);
    }
}
