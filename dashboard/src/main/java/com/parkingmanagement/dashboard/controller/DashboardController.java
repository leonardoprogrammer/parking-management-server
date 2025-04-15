package com.parkingmanagement.dashboard.controller;

import com.parkingmanagement.dashboard.enums.DashboardFilterEnum;
import com.parkingmanagement.dashboard.model.dto.*;
import com.parkingmanagement.dashboard.security.SecurityService;
import com.parkingmanagement.dashboard.service.ParkedVehicleService;
import com.parkingmanagement.dashboard.service.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final ParkedVehicleService parkedVehicleService;
    private final ParkingService parkingService;
    private final SecurityService securityService;

    public DashboardController(ParkedVehicleService parkedVehicleService, ParkingService parkingService, SecurityService securityService) {
        this.parkedVehicleService = parkedVehicleService;
        this.parkingService = parkingService;
        this.securityService = securityService;
    }

    @Operation(summary = "Obtém cartões filtrados com base no estacionamento e no filtro fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartões retornados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar os cartões do estacionamento")
    })
    @GetMapping("/{parkingId}/filtered-cards")
    public ResponseEntity<Object> getFilteredCards(@PathVariable UUID parkingId, @RequestParam String filter) {
        if (!parkingService.existsByUserCreatorIdAndId(securityService.getCurrentUser().getId(), parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        DashboardFilterEnum filterEnum = DashboardFilterEnum.valueOf(filter);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = null;
        LocalDateTime endDate = now;

        switch (filterEnum) {
            case TODAY:
                startDate = now.toLocalDate().atStartOfDay();
                break;
            case YESTERDAY:
                startDate = now.minusDays(1).toLocalDate().atStartOfDay();
                endDate = now.toLocalDate().atStartOfDay();
                break;
            case THIS_WEEK:
                startDate = now.minusDays(now.getDayOfWeek().getValue() - 1).toLocalDate().atStartOfDay();
                break;
            case THIS_MONTH:
                startDate = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
                break;
            case LAST_30_DAYS:
                startDate = now.minusDays(30).toLocalDate().atStartOfDay();
                break;
            case ALL:
                startDate = LocalDateTime.of(1970, 1, 1, 0, 0);
                break;
        }

        BigDecimal revenue = parkedVehicleService.getRevenue(parkingId, startDate, endDate);
        Integer checkInQuantity = parkedVehicleService.getCheckInQuantity(parkingId, startDate, endDate);
        Integer checkOutQuantity = parkedVehicleService.getCheckOutQuantity(parkingId, startDate, endDate);

        ResponseFilteredCardsDTO filteredCardsDTO = new ResponseFilteredCardsDTO(
                revenue,
                checkInQuantity,
                checkOutQuantity
        );

        return ResponseEntity.ok(filteredCardsDTO);
    }

    @Operation(summary = "Obtém gráfico com base no estacionamento e no filtro fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gráfico retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar o gráfico do estacionamento")
    })
    @GetMapping("/{parkingId}/chart")
    public ResponseEntity<Object> getChart(@PathVariable UUID parkingId, @RequestParam String filter) {
        if (!parkingService.existsByUserCreatorIdAndId(securityService.getCurrentUser().getId(), parkingId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        DashboardFilterEnum filterEnum = DashboardFilterEnum.valueOf(filter);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = null;
        LocalDateTime endDate = now;

        switch (filterEnum) {
            case TODAY:
                startDate = now.toLocalDate().atStartOfDay();
                break;
            case YESTERDAY:
                startDate = now.minusDays(1).toLocalDate().atStartOfDay();
                endDate = now.toLocalDate().atStartOfDay();
                break;
            case THIS_WEEK:
                startDate = now.minusDays(now.getDayOfWeek().getValue() - 1).toLocalDate().atStartOfDay();
                break;
            case THIS_MONTH:
                startDate = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
                break;
            case LAST_30_DAYS:
                startDate = now.minusDays(30).toLocalDate().atStartOfDay();
                break;
            case ALL:
                startDate = LocalDateTime.of(1970, 1, 1, 0, 0);
                break;
        }

        // Recuperar dados agrupados por data
        List<Object[]> checkInData = parkedVehicleService.getCheckInDataGroupedByDate(parkingId, startDate, endDate);
        List<Object[]> revenueData = parkedVehicleService.getRevenueDataGroupedByDate(parkingId, startDate, endDate);

        // Mapear os dados para os DTOs
        List<TowerChartItemDTO> items = checkInData.stream().map(data -> {
            Date date = (Date) data[0];
            Long checkInCount = (Long) data[1];
            BigDecimal revenue = revenueData.stream()
                    .filter(r -> r[0].equals(date))
                    .map(r -> (BigDecimal) r[1])
                    .findFirst()
                    .orElse(BigDecimal.ZERO);

            return new TowerChartItemDTO(
                    date.toString(),
                    List.of(
                            new TowerDTO(1, checkInCount),
                            new TowerDTO(2, revenue)
                    )
            );
        }).toList();

        TowerChartDTO towerChartDTO = new TowerChartDTO(
                List.of(
                        new TowerCategoryDTO(1, "Check-ins", "#008DFF"),
                        new TowerCategoryDTO(2, "Ganhos", "#00FF37")
                ),
                items
        );

        return ResponseEntity.ok(towerChartDTO);
    }
}
