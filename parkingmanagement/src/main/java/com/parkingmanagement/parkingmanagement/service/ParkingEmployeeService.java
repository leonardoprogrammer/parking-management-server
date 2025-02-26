package com.parkingmanagement.parkingmanagement.service;

import com.parkingmanagement.parkingmanagement.model.entity.ParkingEmployee;
import com.parkingmanagement.parkingmanagement.repository.ParkingEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingEmployeeService {

    @Autowired
    private ParkingEmployeeRepository parkingEmployeeRepository;

    public Optional<ParkingEmployee> findById(UUID id) {
        return parkingEmployeeRepository.findById(id);
    }

    public Optional<ParkingEmployee> findByParkingId(UUID parkingId) {
        return parkingEmployeeRepository.findByParkingId(parkingId);
    }

    public List<ParkingEmployee> findByUserId(UUID userId) {
        return parkingEmployeeRepository.findByUserId(userId);
    }

    public ParkingEmployee save(ParkingEmployee parkingEmployee) {
        return parkingEmployeeRepository.save(parkingEmployee);
    }

    public void deleteById(UUID id) {
        parkingEmployeeRepository.deleteById(id);
    }
}
