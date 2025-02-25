package com.parkingmanagement.parkingmanagement.service;

import com.parkingmanagement.parkingmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }
}
