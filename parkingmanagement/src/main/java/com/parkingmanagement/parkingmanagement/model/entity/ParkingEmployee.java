package com.parkingmanagement.parkingmanagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parking_employee")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParkingEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;

    @ManyToOne
    @JoinColumn(name = "employee_user_id", nullable = false)
    private User employeeUser;

    @ManyToOne
    @JoinColumn(name = "adder_user_id", nullable = false)
    private User adderUser;

    @Column(nullable = false)
    private LocalDateTime dateInc = LocalDateTime.now();

    private LocalDateTime dateAlt;
}
