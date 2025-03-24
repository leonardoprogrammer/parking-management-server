package com.parkingmanagement.parkingmanagement.controller;

import com.parkingmanagement.parkingmanagement.model.dto.RequestUpdateParkingSettingsDTO;
import com.parkingmanagement.parkingmanagement.model.entity.*;
import com.parkingmanagement.parkingmanagement.security.SecurityService;
import com.parkingmanagement.parkingmanagement.service.ParkingService;
import com.parkingmanagement.parkingmanagement.service.ParkingSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Retorna configurações do estacionamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configurações do estacionamento retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Configurações do estacionamento não encontradas"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar as configurações do estacionamento")
    })
    @GetMapping
    public ResponseEntity<Object> getParkingSettings(@RequestParam UUID parkingId) {
        ParkingSettings parkingSettings = parkingSettingsService.findByParkingId(parkingId).orElse(null);
        if (parkingSettings == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.currentUserIsOwner(parkingId) && !securityService.currentUserIsEmployeeAndCanEditParking(parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(parkingSettings);
    }

    @Operation(summary = "Cria configurações do estacionamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configurações do estacionamento criadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estacionamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para criar configurações do estacionamento"),
            @ApiResponse(responseCode = "409", description = "Configurações do estacionamento já existem")
    })
    @PostMapping
    public ResponseEntity<Object> createParkingSettings(@RequestParam UUID parkingId) {
        Parking parking = parkingService.findById(parkingId).orElse(null);
        if (parking == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.currentUserIsOwner(parkingId) && !securityService.currentUserIsEmployeeAndCanEditParking(parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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

    @Operation(summary = "Atualiza configurações do estacionamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configurações do estacionamento atualizadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Configurações do estacionamento não encontradas"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para atualizar configurações do estacionamento"),
            @ApiResponse(responseCode = "400", description = "Tempo mínimo para cobrança não informado")
    })
    @PutMapping
    public ResponseEntity<Object> updateParkingSettings(@RequestParam UUID parkingId,
                                                        @Valid @RequestBody RequestUpdateParkingSettingsDTO requestUpdateParkingSettingsDTO) {
        ParkingSettings parkingSettings = parkingSettingsService.findByParkingId(parkingId).orElse(null);
        if (parkingSettings == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.currentUserIsOwner(parkingId) && !securityService.currentUserIsEmployeeAndCanEditParking(parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
