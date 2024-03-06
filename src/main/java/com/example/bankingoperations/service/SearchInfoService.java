package com.example.bankingoperations.service;

import com.example.bankingoperations.dto.*;
import com.example.bankingoperations.service.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface SearchInfoService {

    Page<UserEntity> searchUsers(String email, String phone, String name, LocalDate dob, Pageable pageable);

    Page<UserEntity> searchUsersByEmail(String email, Pageable pageable);

    Page<UserEntity> searchUsersByPhone(String phone, Pageable pageable);

    Page<UserEntity> searchUsersByName(String name, Pageable pageable);

    Page<UserEntity> searchUsersByDob(LocalDate dob, Pageable pageable);
}
