package com.parkingmanagement.auth.repository;

import com.parkingmanagement.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u " +
            "WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) " +
            "AND (:cpf IS NULL OR u.cpf LIKE CONCAT('%', :cpf, '%'))")
    List<User> searchUsers(@Param("name") String name, @Param("email") String email, @Param("cpf") String cpf);
}
