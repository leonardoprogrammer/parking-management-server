package com.parkingmanagement.auth.repository;

import com.parkingmanagement.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
