package com.parkingmanagement.parkedvehicles.repository;

import com.parkingmanagement.parkedvehicles.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsById(UUID id);

    Optional<User> findByEmail(String email);
}
