package com.parkingmanagement.parkingusermanagement.controller;

import com.parkingmanagement.parkingusermanagement.entity.ParkingUserLink;
import com.parkingmanagement.parkingusermanagement.service.ParkingUserLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-user-links")
public class ParkingUserLinkController {

    @Autowired
    private ParkingUserLinkService parkingUserLinkService;

    @PostMapping
    public ResponseEntity<ParkingUserLink> createParkingUserLink(@RequestBody ParkingUserLink parkingUserLink) {
        ParkingUserLink createdLink = parkingUserLinkService.createParkingUserLink(parkingUserLink);
        return new ResponseEntity<>(createdLink, HttpStatus.CREATED);
    }

    @GetMapping("/parking-lot/{parkingLotId}")
    public ResponseEntity<List<ParkingUserLink>> getUsersByParkingLotId(@PathVariable Long parkingLotId) {
        List<ParkingUserLink> users = parkingUserLinkService.getUsersByParkingLotId(parkingLotId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParkingUserLink(@PathVariable Long id) {
        parkingUserLinkService.deleteParkingUserLink(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
