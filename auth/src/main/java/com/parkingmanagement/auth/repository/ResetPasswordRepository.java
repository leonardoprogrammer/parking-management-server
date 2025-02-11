package com.parkingmanagement.auth.repository;

import com.parkingmanagement.auth.model.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, UUID> {
}
