package com.example.bankingoperations.service.repositories;

import com.example.bankingoperations.service.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByLogin(String userLogin);
    Optional<UserEntity> findByLogin(String userLogin);
    Page<UserEntity> findAll(Specification<UserEntity> spec, Pageable pageable);
}
