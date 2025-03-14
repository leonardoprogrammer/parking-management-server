package com.parkingmanagement.parkingmanagement.service;

import com.parkingmanagement.parkingmanagement.model.entity.Parking;
import com.parkingmanagement.parkingmanagement.model.entity.ParkingEmployee;
import com.parkingmanagement.parkingmanagement.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final ParkedVehicleService parkedVehicleService;
    private final ParkingEmployeeService parkingEmployeeService;
    private final ParkingSettingsService parkingSettingsService;
    private final EmployeePermissionsService employeePermissionsService;

    public ParkingService(ParkingRepository parkingRepository, ParkedVehicleService parkedVehicleService, ParkingEmployeeService parkingEmployeeService, ParkingSettingsService parkingSettingsService, EmployeePermissionsService employeePermissionsService) {
        this.parkingRepository = parkingRepository;
        this.parkedVehicleService = parkedVehicleService;
        this.parkingEmployeeService = parkingEmployeeService;
        this.parkingSettingsService = parkingSettingsService;
        this.employeePermissionsService = employeePermissionsService;
    }

    public Optional<Parking> findById(UUID id) {
        return parkingRepository.findById(id);
    }

    public boolean existsById(UUID id) {
        return parkingRepository.existsById(id);
    }

    public List<Parking> findByUserCreatorId(UUID userCreatorId) {
        return parkingRepository.findByUserCreatorId(userCreatorId);
    }

    public Parking save(Parking parking) {
        return parkingRepository.save(parking);
    }

    public List<Parking> findByEmployeeUserId(UUID userId) {
        List<ParkingEmployee> employees = parkingEmployeeService.findByUserId(userId);
        return employees.stream()
                .map(employee -> parkingRepository.findById(employee.getParkingId()).orElse(null))
                .collect(Collectors.toList());
    }

    public void delete(UUID parkingId) {
        parkedVehicleService.deleteByParkingId(parkingId);

        List<ParkingEmployee> employees = parkingEmployeeService.findEmployeesByParkingId(parkingId);
        for (ParkingEmployee employee : employees) {
            employeePermissionsService.deleteByEmployeeId(employee.getId());
            parkingEmployeeService.deleteById(employee.getId());
        }

        parkingSettingsService.deleteByParkingId(parkingId);

        parkingRepository.deleteById(parkingId);
    }

    public boolean existsByUserCreatorIdAndId(UUID userId, UUID parkingId) {
        return parkingRepository.existsByUserCreatorIdAndId(userId, parkingId);
    }

    @Transactional
    public void deleteById(UUID id) {
        parkingRepository.deleteById(id);
    }
}
