package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.dto.RequestUpdateParkingSettingsDTO;
import com.parkingmanagement.parkingmanagement.model.entity.*;
import com.parkingmanagement.parkingmanagement.security.SecurityService;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import com.parkingmanagement.parkingmanagement.service.ParkingSettingsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequestMapping("/parking-settings")
public class ParkingSettingsController {

    private final ParkingSettingsService parkingSettingsService;
    private final SecurityService securityService;
    private final ParkingService parkingService;

    public ParkingSettingsController(ParkingSettingsService parkingSettingsService, SecurityService securityService, ParkingService parkingService) {
        this.parkingSettingsService = parkingSettingsService;
        this.securityService = securityService;
        this.parkingService = parkingService;
    }

    @GetMapping
    public ResponseEntity<Object> getParkingSettings(@RequestParam UUID parkingId) {
        ParkingSettings parkingSettings = parkingSettingsService.findByParkingId(parkingId).orElse(null);
        if (parkingSettings == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.currentUserIsOwner(parkingId) && !securityService.currentUserIsEmployeeAndCanEditParking(parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(parkingSettings);
    }

    @PostMapping
    public ResponseEntity<Object> createParkingSettings(@RequestParam UUID parkingId) {
        Parking parking = parkingService.findById(parkingId).orElse(null);
        if (parking == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.currentUserIsOwner(parkingId) && !securityService.currentUserIsEmployeeAndCanEditParking(parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ParkingSettings parkingSettings = parkingSettingsService.findByParkingId(parkingId).orElse(null);
        if (parkingSettings != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        parkingSettings = new ParkingSettings(
                parking.getId(),
                true,
                null,
                LocalTime.of(0, 0),
                BigDecimal.ZERO
        );
        ParkingSettings savedParkingSettings = parkingSettingsService.save(parkingSettings);

        return ResponseEntity.ok(savedParkingSettings);
    }

    @PutMapping
    public ResponseEntity<Object> updateParkingSettings(@RequestParam UUID parkingId,
                                                        @Valid @RequestBody RequestUpdateParkingSettingsDTO requestUpdateParkingSettingsDTO) {
        ParkingSettings parkingSettings = parkingSettingsService.findByParkingId(parkingId).orElse(null);
        if (parkingSettings == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.currentUserIsOwner(parkingId) && !securityService.currentUserIsEmployeeAndCanEditParking(parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!requestUpdateParkingSettingsDTO.isChargeFromCheckIn() && requestUpdateParkingSettingsDTO.getMinimumTimeToCharge() == null) {
            return ResponseEntity.badRequest().body("Informe o tempo mínimo para cobrança");
        }

        parkingSettings.setChargeFromCheckIn(requestUpdateParkingSettingsDTO.isChargeFromCheckIn());
        parkingSettings.setMinimumTimeToCharge(requestUpdateParkingSettingsDTO.getMinimumTimeToCharge());
        parkingSettings.setPeriod(requestUpdateParkingSettingsDTO.getPeriod());
        parkingSettings.setValuePerPeriod(requestUpdateParkingSettingsDTO.getValuePerPeriod());
        parkingSettingsService.save(parkingSettings);

        return ResponseEntity.ok().build();
    }
}
