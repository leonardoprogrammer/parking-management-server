package com.parkingmanagement.auth.service;

import com.parkingmanagement.auth.model.entity.ResetPassword;
import com.parkingmanagement.auth.repository.ResetPasswordRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ResetPasswordService {

    private final ResetPasswordRepository resetPasswordRepository;

    public ResetPasswordService(ResetPasswordRepository resetPasswordRepository) {
        this.resetPasswordRepository = resetPasswordRepository;
    }

    public ResetPassword save(ResetPassword resetPassword) {
        return resetPasswordRepository.save(resetPassword);
    }

    public Optional<ResetPassword> findByIdAndEmail(UUID id, String email) {
        return resetPasswordRepository.findByIdAndEmail(id, email);
    }
}
