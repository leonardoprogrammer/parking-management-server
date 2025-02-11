package com.parkingmanagement.parkedvehicles.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parked_vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkedVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;

    @Column(nullable = false)
    private String plate;

    @Column(nullable = false)
    private String model;

    private String color;

    private String space;

    @Column(nullable = false)
    private LocalDateTime entryDate;

    @ManyToOne
    @JoinColumn(name = "checkin_employee_id", nullable = false)
    private User checkinEmployee;

    private LocalDateTime checkoutDate;

    @ManyToOne
    @JoinColumn(name = "checkout_employee_id")
    private User checkoutEmployee;

    @Column(nullable = false)
    private boolean paid;

    private String paymentMethod;

    @Column(nullable = false)
    private LocalDateTime dateInc = LocalDateTime.now();

    private LocalDateTime dateAlt;
}
