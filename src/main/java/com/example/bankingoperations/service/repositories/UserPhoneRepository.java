package com.example.bankingoperations.service.repositories;

import com.example.bankingoperations.service.entities.UserPhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPhoneRepository extends JpaRepository<UserPhoneEntity, Integer> {

    Boolean existsByPhone(String userPhone);

    UserPhoneEntity findByPhone(String phone);

}
