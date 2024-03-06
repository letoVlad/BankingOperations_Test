package com.example.bankingoperations.service.repositories;

import com.example.bankingoperations.service.entities.UserEmailEntity;
import com.example.bankingoperations.service.entities.UserPhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEmailRepository extends JpaRepository<UserEmailEntity, Integer> {

    Boolean existsByEmail(String userEmail);

    UserEmailEntity findByEmail(String email);

    void deleteByEmail(String email);
}
