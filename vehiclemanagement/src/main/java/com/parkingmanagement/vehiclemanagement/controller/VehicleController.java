package com.parkingmanagement.vehiclemanagement.controller;

import com.parkingmanagement.vehiclemanagement.entity.VehicleRecord;
import com.parkingmanagement.vehiclemanagement.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/entry")
    public ResponseEntity<VehicleRecord> registerEntry(@RequestBody VehicleRecord vehicleRecord) {
        VehicleRecord createdRecord = vehicleService.registerEntry(vehicleRecord);
        return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
    }

    @PostMapping("/exit/{id}")
    public ResponseEntity<VehicleRecord> registerExit(@PathVariable Long id) {
        VehicleRecord updatedRecord = vehicleService.registerExit(id);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
    }

    @GetMapping("/parking-lot/{parkingLotId}")
    public ResponseEntity<List<VehicleRecord>> getRecordsByParkingLot(@PathVariable Long parkingLotId) {
        List<VehicleRecord> records = vehicleService.getRecordsByParkingLot(parkingLotId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
}
