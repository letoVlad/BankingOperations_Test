package com.example.bankingoperations.service.impl;

import com.example.bankingoperations.service.SearchInfoService;
import com.example.bankingoperations.service.entities.UserEntity;
import com.example.bankingoperations.service.repositories.*;
import com.example.bankingoperations.specification.UserSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service

public class SearchInfoServiceImpl implements SearchInfoService {
    private final UserRepository userRepository;


    public SearchInfoServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public Page<UserEntity> searchUsers(String email, String phone, String name, LocalDate dob, Pageable pageable) {
        Specification<UserEntity> spec = Specification.where(null);

        if (email != null) {
            spec = spec.and(UserSpecifications.hasEmail(email));
        }
        if (phone != null) {
            spec = spec.and(UserSpecifications.hasPhone(phone));
        }
        if (name != null) {
            spec = spec.and(UserSpecifications.nameLike(name));
        }
        if (dob != null) {
            spec = spec.and(UserSpecifications.dobAfter(dob));
        }

        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Page<UserEntity> searchUsersByEmail(String email, Pageable pageable) {
        if (email == null) {
            throw new IllegalArgumentException("Ошибка: нулевое значение");
        }
        Specification<UserEntity> spec = UserSpecifications.hasEmail(email);
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Page<UserEntity> searchUsersByPhone(String phone, Pageable pageable) {
        if (phone == null) {
            throw new IllegalArgumentException("Ошибка: нулевое значение");
        }
        Specification<UserEntity> spec = UserSpecifications.hasPhone(phone);
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Page<UserEntity> searchUsersByName(String name, Pageable pageable) {
        if (name == null) {
            throw new IllegalArgumentException("Ошибка: нулевое значение");
        }
        Specification<UserEntity> spec = UserSpecifications.nameLike(name);
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Page<UserEntity> searchUsersByDob(LocalDate dob, Pageable pageable) {
        if (dob == null) {
            throw new IllegalArgumentException("Ошибка: нулевое значение");
        }
        Specification<UserEntity> spec = UserSpecifications.dobAfter(dob);
        return userRepository.findAll(spec, pageable);
    }

}
