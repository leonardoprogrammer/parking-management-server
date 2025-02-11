package com.parkingmanagement.auth.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reset_password")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean sentEmail;

    @Column(nullable = false)
    private boolean reset;

    private LocalDateTime dateReset;

    @Column(nullable = false)
    private LocalDateTime dateInc = LocalDateTime.now();

    private LocalDateTime dateAlt;
}
