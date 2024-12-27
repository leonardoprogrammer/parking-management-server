package com.parkingmanagement.parkingusermanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_user_links")
public class ParkingUserLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long parkingLotId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 20)
    private String role; // "ADMIN" or "EMPLOYEE"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Long parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
