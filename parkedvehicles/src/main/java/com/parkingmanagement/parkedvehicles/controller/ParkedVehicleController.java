package com.parkingmanagement.parkedvehicles.controller;

import com.parkingmanagement.parkedvehicles.model.dto.RequestCheckinParkedVehicleDTO;
import com.parkingmanagement.parkedvehicles.model.dto.RequestCheckoutParkedVehicleDTO;
import com.parkingmanagement.parkedvehicles.model.dto.ResponseCheckinParkedVehicleDTO;
import com.parkingmanagement.parkedvehicles.model.dto.ResponseFullParkedVehicleDTO;
import com.parkingmanagement.parkedvehicles.model.entity.ParkedVehicle;
import com.parkingmanagement.parkedvehicles.model.entity.ParkingSettings;
import com.parkingmanagement.parkedvehicles.model.entity.User;
import com.parkingmanagement.parkedvehicles.security.SecurityService;
import com.parkingmanagement.parkedvehicles.security.SecurityUtils;
import com.parkingmanagement.parkedvehicles.service.*;
import com.parkingmanagement.parkedvehicles.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    private final ParkingSettingsService parkingSettingsService;
    private final ParkingFeeCalculatorService parkingFeeCalculatorService;

    public ParkedVehicleController(ParkedVehicleService parkedVehicleService, SecurityService securityService, ParkingService parkingService, UserService userService, ParkingSettingsService parkingSettingsService, ParkingFeeCalculatorService parkingFeeCalculatorService) {
        this.parkedVehicleService = parkedVehicleService;
        this.securityService = securityService;
        this.parkingService = parkingService;
        this.userService = userService;
        this.parkingSettingsService = parkingSettingsService;
        this.parkingFeeCalculatorService = parkingFeeCalculatorService;
    }

    @Operation(summary = "Obtém um veículo estacionado pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo estacionado encontrado"),
            @ApiResponse(responseCode = "404", description = "Veículo estacionado não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/{parkedVehicleId}")
    public ResponseEntity<Object> getById(@PathVariable UUID parkedVehicleId) {
        ParkedVehicle parkedVehicle = parkedVehicleService.findById(parkedVehicleId).orElse(null);
        if (parkedVehicle == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkedVehicle.getParkingId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User checkinEmployeeUser = userService.findById(parkedVehicle.getCheckinEmployeeId()).orElse(null);
        User checkoutEmployeeUser = parkedVehicle.getCheckoutEmployeeId() != null ? userService.findById(parkedVehicle.getCheckoutEmployeeId()).orElse(null) : null;

        ResponseFullParkedVehicleDTO responseFullParkedVehicleDTO = new ResponseFullParkedVehicleDTO(
                parkedVehicle.getId(),
                parkedVehicle.getParkingId(),
                parkedVehicle.getPlate(),
                parkedVehicle.getModel(),
                parkedVehicle.getColor(),
                parkedVehicle.getSpace(),
                parkedVehicle.getEntryDate(),
                checkinEmployeeUser.getId(),
                checkinEmployeeUser.getName(),
                parkedVehicle.getCheckoutDate(),
                checkoutEmployeeUser != null ? checkoutEmployeeUser.getId() : null,
                checkoutEmployeeUser != null ? checkoutEmployeeUser.getName() : null,
                parkedVehicle.isPaid(),
                Utils.formatToBRL(parkedVehicle.getAmountPaid()),
                parkedVehicle.getPaymentMethod(),
                parkedVehicle.getCreatedAt()
        );

        return ResponseEntity.ok(responseFullParkedVehicleDTO);
    }

    @Operation(summary = "Obtém os detalhes do check-in de um veículo estacionado pelo ID do veículo")
    @GetMapping("/{parkedVehicleId}/checkin")
    public ResponseEntity<Object> getCheckinParkedVehicleById(@PathVariable UUID parkedVehicleId) {
        ParkedVehicle parkedVehicle = parkedVehicleService.findById(parkedVehicleId).orElse(null);
        if (parkedVehicle == null) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkedVehicle.getParkingId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User userEmployee = userService.findById(parkedVehicle.getCheckinEmployeeId()).orElse(null);

        ParkingSettings parkingSettings = parkingSettingsService.findByParkingId(parkedVehicle.getParkingId()).orElse(null);
        BigDecimal amountToPay = parkingSettings != null ? parkingFeeCalculatorService.calculatorFee(parkingSettings, parkedVehicle.getEntryDate(), LocalDateTime.now()) : null;

        ResponseCheckinParkedVehicleDTO responseCheckinParkedVehicleDTO = new ResponseCheckinParkedVehicleDTO(
                parkedVehicle.getParkingId(),
                parkedVehicle.getPlate(),
                parkedVehicle.getModel(),
                parkedVehicle.getColor(),
                parkedVehicle.getSpace(),
                parkedVehicle.getEntryDate(),
                amountToPay,
                userEmployee.getName(),
                parkedVehicle.getCreatedAt()
        );

        return ResponseEntity.ok(responseCheckinParkedVehicleDTO);
    }

    @Operation(summary = "Obtém veículos estacionados pelo ID do estacionamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículos estacionados encontrados"),
            @ApiResponse(responseCode = "404", description = "Veículos estacionados não encontrados"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<List<ParkedVehicle>> getParkedVehiclesByParkingId(@PathVariable UUID parkingId) {
        List<ParkedVehicle> parkedVehicles = parkedVehicleService.findParkedVehiclesByParkingId(parkingId);

        if (parkedVehicles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(parkedVehicles);
    }

    @Operation(summary = "Obtém lista do histórico de veículos estacionados pelo ID do estacionamentoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de veículos estacionados encontrado"),
            @ApiResponse(responseCode = "404", description = "Histórico de veículos estacionados não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/history")
    public ResponseEntity<Object> getHistoryByParkingId(@RequestParam UUID parkingId, @RequestParam Integer page, @RequestParam Integer sizePage) {
        if (!parkingService.existsById(parkingId)) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(parkedVehicleService.getParkedVehiclesHistoryByParkingId(parkingId, page, sizePage));
    }

    @Operation(summary = "Obtém o total de páginas do histórico de veículos estacionados pelo ID do estacionamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total de páginas do histórico de veículos estacionados encontrado"),
            @ApiResponse(responseCode = "404", description = "Total de páginas do histórico de veículos estacionados não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/history/totalPages")
    public ResponseEntity<Object> getTotalHistoryByParkingId(@RequestParam UUID parkingId, @RequestParam Integer sizePage) {
        if (!parkingService.existsById(parkingId)) {
            return ResponseEntity.badRequest().body("Não há estacionamento com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(parkedVehicleService.getTotalPagesOfParkedVehiclesHistory(parkingId, sizePage));
    }

    @Operation(summary = "Realiza o check-in de um veículo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check-in realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar check-in")
    })
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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
                requestCheckinParkedVehicleDTO.getPlate().toUpperCase(),
                requestCheckinParkedVehicleDTO.getModel().toUpperCase(),
                requestCheckinParkedVehicleDTO.getColor().toUpperCase(),
                !requestCheckinParkedVehicleDTO.getSpace().isBlank() ? requestCheckinParkedVehicleDTO.getSpace().toUpperCase() : null,
                entryDate,
                userEmployeeId
        );

        ParkedVehicle savedParkedVehicle = parkedVehicleService.save(newParkedVehicle);

        return ResponseEntity.ok(savedParkedVehicle);
    }

    @Operation(summary = "Realiza o checkout de um veículo estacionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checkout realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao realizar checkout")
    })
    @PostMapping("/checkout")
    public ResponseEntity<Object> checkout(@Valid @RequestBody RequestCheckoutParkedVehicleDTO requestCheckoutParkedVehicleDTO) {
        ParkedVehicle parkedVehicle = parkedVehicleService.findById(UUID.fromString(requestCheckoutParkedVehicleDTO.getParkedVehicleId())).orElse(null);
        UUID checkoutEmployeeId = UUID.fromString(requestCheckoutParkedVehicleDTO.getCheckoutEmployeeId());

        if (parkedVehicle == null) {
            return ResponseEntity.badRequest().body("Não há veículo estacionado com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(SecurityUtils.getCurrentUserEmail(), parkedVehicle.getParkingId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (parkedVehicle.getCheckoutDate() != null) {
            return ResponseEntity.badRequest().body("Já há checkout deste veículo");
        }

        User checkoutUser = userService.findById(checkoutEmployeeId).orElse(null);
        if (checkoutUser == null) {
            return ResponseEntity.badRequest().body("Não há usuário com este ID");
        }

        if (!securityService.userIsOwnerOrEmployee(checkoutUser.getEmail(), parkedVehicle.getParkingId())) {
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

        if (requestCheckoutParkedVehicleDTO.isPaid() && requestCheckoutParkedVehicleDTO.getAmountPaid() == null) {
            return ResponseEntity.badRequest().body("O valor pago é obrigatório quando o pagamento é efetuado");
        }

        if (requestCheckoutParkedVehicleDTO.getAmountPaid() != null && !requestCheckoutParkedVehicleDTO.isPaid()) {
            return ResponseEntity.badRequest().body("O pagamento não foi efetuado, mas um valor foi informado");
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
        parkedVehicle.setAmountPaid(requestCheckoutParkedVehicleDTO.getAmountPaid());
        parkedVehicle.setPaymentMethod(requestCheckoutParkedVehicleDTO.getPaymentMethod());
        parkedVehicle.setUpdatedAt(LocalDateTime.now());
        parkedVehicleService.save(parkedVehicle);

        return ResponseEntity.ok().build();
    }
}
