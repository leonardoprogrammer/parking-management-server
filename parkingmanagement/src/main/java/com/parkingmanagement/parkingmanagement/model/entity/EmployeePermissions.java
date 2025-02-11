package com.parkingmanagement.parkingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "employee_permissions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeePermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "parking_employee_id", nullable = false)
    private ParkingEmployee parkingEmployee;

    @Column(nullable = false)
    private boolean checkinVehicle;

    @Column(nullable = false)
    private boolean checkoutVehicle;

    @Column(nullable = false)
    private boolean addEmployee;

    @Column(nullable = false)
    private boolean changePermissions;

    @Column(nullable = false)
    private boolean editParking;

    @Column(nullable = false)
    private LocalDateTime dateInc = LocalDateTime.now();

    private LocalDateTime dateAlt;

    @ManyToOne
    @JoinColumn(name = "user_alt", nullable = false)
    private User userAlt;
}
