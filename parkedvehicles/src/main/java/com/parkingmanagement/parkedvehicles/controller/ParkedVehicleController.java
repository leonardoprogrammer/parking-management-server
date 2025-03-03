package com.parkingmanagement.parkedvehicles.controller;

import com.parkingmanagement.parkedvehicles.model.dto.RequestCheckinParkedVehicleDTO;
import com.parkingmanagement.parkedvehicles.model.dto.RequestCheckoutParkedVehicleDTO;
import com.parkingmanagement.parkedvehicles.model.dto.ResponseCheckinParkedVehicleDTO;
import com.parkingmanagement.parkedvehicles.model.entity.ParkedVehicle;
import com.parkingmanagement.parkedvehicles.model.entity.User;
import com.parkingmanagement.parkedvehicles.security.SecurityService;
import com.parkingmanagement.parkedvehicles.security.SecurityUtils;
import com.parkingmanagement.parkedvehicles.service.ParkedVehicleService;
import com.parkingmanagement.parkedvehicles.service.ParkingService;
import com.parkingmanagement.parkedvehicles.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/parked-vehicle")
public class ParkedVehicleController {

    private final ParkedVehicleService parkedVehicleService;
    private final SecurityService securityService;
    private final ParkingService parkingService;
    private final UserService userService;

    public ParkedVehicleController(ParkedVehicleService parkedVehicleService, SecurityService securityService, ParkingService parkingService, UserService userService) {
        this.parkedVehicleService = parkedVehicleService;
        this.securityService = securityService;
        this.parkingService = parkingService;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable UUID id) {
        ParkedVehicle parkedVehicle = parkedVehicleService.findById(id).orElse(null);
        if (parkedVehicle == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkedVehicle.getParkingId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(parkedVehicle);
    }

    @GetMapping("/{id}/checkin")
    public ResponseEntity<Object> getCheckinParkedVehicleById(@PathVariable UUID id) {
        ParkedVehicle parkedVehicle = parkedVehicleService.findById(id).orElse(null);
        if (parkedVehicle == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkedVehicle.getParkingId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User userEmployee = userService.findById(parkedVehicle.getCheckinEmployeeId()).orElse(null);

        ResponseCheckinParkedVehicleDTO responseCheckinParkedVehicleDTO = new ResponseCheckinParkedVehicleDTO(
                parkedVehicle.getParkingId(),
                parkedVehicle.getPlate(),
                parkedVehicle.getModel(),
                parkedVehicle.getColor(),
                parkedVehicle.getSpace(),
                parkedVehicle.getEntryDate(),
                userEmployee.getName(),
                parkedVehicle.getCreatedAt()
        );

        return ResponseEntity.ok(responseCheckinParkedVehicleDTO);
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

    @GetMapping("/history")
    public ResponseEntity<Object> getHistoryByParkingId(@RequestParam UUID parkingId, @RequestParam Integer page, @RequestParam Integer sizePage) {
        if (!parkingService.existsById(parkingId)) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(parkedVehicleService.getParkedVehiclesHistoryByParkingId(parkingId, page, sizePage));
    }

    @GetMapping("/history/totalPages")
    public ResponseEntity<Object> getTotalHistoryByParkingId(@RequestParam UUID parkingId, @RequestParam Integer sizePage) {
        if (!parkingService.existsById(parkingId)) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(parkedVehicleService.getTotalPagesOfParkedVehiclesHistory(parkingId, sizePage));
    }

    @PostMapping("/checkin")
    public ResponseEntity<Object> checkin(@Valid @RequestBody RequestCheckinParkedVehicleDTO requestCheckinParkedVehicleDTO) {
        UUID parkingId = UUID.fromString(requestCheckinParkedVehicleDTO.getParkingId());
        UUID userEmployeeId = UUID.fromString(requestCheckinParkedVehicleDTO.getCheckinEmployeeId());
        LocalDateTime entryDate;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try {
            entryDate = LocalDateTime.parse(requestCheckinParkedVehicleDTO.getEntryDate(), formatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("A data de entrada é inválida");
        }

        if (!parkingService.existsById(parkingId)) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkingId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findById(userEmployeeId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Não há usuário com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(user.getEmail(), parkingId)) {
            return ResponseEntity.badRequest().body("O usuário não é funcionário deste estacionamento");
        }

        if (entryDate.isAfter(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("A data de entrada não pode ser maior que a data atual");
        }

        ParkedVehicle newParkedVehicle = new ParkedVehicle(
                parkingId,
                requestCheckinParkedVehicleDTO.getPlate(),
                requestCheckinParkedVehicleDTO.getModel(),
                requestCheckinParkedVehicleDTO.getColor(),
                requestCheckinParkedVehicleDTO.getSpace(),
                entryDate,
                userEmployeeId
        );

        ParkedVehicle savedParkedVehicle = parkedVehicleService.save(newParkedVehicle);

        return ResponseEntity.ok(savedParkedVehicle);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Object> checkout(@Valid @RequestBody RequestCheckoutParkedVehicleDTO requestCheckoutParkedVehicleDTO) {
        ParkedVehicle parkedVehicle = parkedVehicleService.findById(UUID.fromString(requestCheckoutParkedVehicleDTO.getParkedVehicleId())).orElse(null);
        UUID checkoutEmployeeId = UUID.fromString(requestCheckoutParkedVehicleDTO.getCheckoutEmployeeId());

        if (parkedVehicle == null) {
            return ResponseEntity.badRequest().body("Não há veículo estacionado com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkedVehicle.getParkingId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userService.findById(checkoutEmployeeId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Não há usuário com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(user.getEmail(), parkedVehicle.getParkingId())) {
            return ResponseEntity.badRequest().body("O usuário não é funcionário deste estacionamento");
        }

        LocalDateTime checkoutDate;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        try {
            checkoutDate = LocalDateTime.parse(requestCheckoutParkedVehicleDTO.getCheckoutDate(), formatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("A data de saída é inválida");
        }

        if (checkoutDate.isBefore(parkedVehicle.getEntryDate())) {
            return ResponseEntity.badRequest().body("A data de saída não pode ser menor que a data de entrada");
        }

        if (requestCheckoutParkedVehicleDTO.isPaid()
                && (requestCheckoutParkedVehicleDTO.getPaymentMethod() == null || requestCheckoutParkedVehicleDTO.getPaymentMethod().isBlank())) {
            return ResponseEntity.badRequest().body("O método de pagamento é obrigatório quando o pagamento é efetuado");
        }

        if ((requestCheckoutParkedVehicleDTO.getPaymentMethod() != null && !requestCheckoutParkedVehicleDTO.getPaymentMethod().isBlank())
                && !requestCheckoutParkedVehicleDTO.isPaid()) {
            return ResponseEntity.badRequest().body("O pagamento não foi efetuado, mas um método de pagamento foi informado");
        }

        parkedVehicle.setCheckoutDate(checkoutDate);
        parkedVehicle.setCheckoutEmployeeId(checkoutEmployeeId);
        parkedVehicle.setPaid(requestCheckoutParkedVehicleDTO.isPaid());
        parkedVehicle.setPaymentMethod(requestCheckoutParkedVehicleDTO.getPaymentMethod());
        parkedVehicle.setUpdatedAt(LocalDateTime.now());
        parkedVehicleService.save(parkedVehicle);

        return ResponseEntity.ok().build();
    }
}
