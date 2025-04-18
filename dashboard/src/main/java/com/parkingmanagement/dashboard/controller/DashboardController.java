package com.parkingmanagement.dashboard.controller;

import com.parkingmanagement.dashboard.enums.DashboardFilterEnum;
import com.parkingmanagement.dashboard.model.dto.*;
import com.parkingmanagement.dashboard.model.vo.ParkedVehicleBasicVO;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    @GetMapping("/{parkingId}/chart/tower/checkins-and-earnings")
    public ResponseEntity<Object> getCheckinsAndEarningsTowerChart(@PathVariable UUID parkingId, @RequestParam String filter) {
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

        List<ParkedVehicleBasicVO> parkedVehicleBasicVOList = parkedVehicleService.getParkedVehicleWithCheckInAndRevenue(parkingId, startDate, endDate);

        // define formato de data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // agrupa pelo "entryDate" e ordena por data
        Map<LocalDateTime, List<ParkedVehicleBasicVO>> groupedByDate = parkedVehicleBasicVOList.stream()
                .collect(Collectors.groupingBy(ParkedVehicleBasicVO::getEntryDate));

        // Sort the grouped data by entryDate
        List<Map.Entry<LocalDateTime, List<ParkedVehicleBasicVO>>> sortedEntries = groupedByDate.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());

        List<String> labels = new ArrayList<>();
        List<Object> checkInsData = new ArrayList<>();
        List<Object> revenueData = new ArrayList<>();

        // popula labels e datasets
        for (Map.Entry<LocalDateTime, List<ParkedVehicleBasicVO>> entry : sortedEntries) {
            LocalDateTime date = entry.getKey();
            List<ParkedVehicleBasicVO> vehicles = entry.getValue();

            labels.add(date.format(formatter)); // Format date as dd/MM/yyyy
            checkInsData.add(vehicles.size());
            revenueData.add(vehicles.stream()
                    .map(ParkedVehicleBasicVO::getAmountPaid)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
        }

        // cria os datasets
        DatasetDTO checkInsDataset = new DatasetDTO();
        checkInsDataset.setLabel("Check-ins");
        checkInsDataset.setData(checkInsData);
        checkInsDataset.setBackgroundColor("#007bff");

        DatasetDTO revenueDataset = new DatasetDTO();
        revenueDataset.setLabel("Ganhos");
        revenueDataset.setData(revenueData);
        revenueDataset.setBackgroundColor("#28a745");

        // adiciona datasets à lista
        List<DatasetDTO> datasets = new ArrayList<>();
        datasets.add(checkInsDataset);
        datasets.add(revenueDataset);

        ResponseTowerChartDTO responseTowerChartDTO = new ResponseTowerChartDTO(labels, datasets);

        return ResponseEntity.ok(responseTowerChartDTO);
    }
}
