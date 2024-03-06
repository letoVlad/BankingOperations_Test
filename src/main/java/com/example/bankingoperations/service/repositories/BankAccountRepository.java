package com.example.bankingoperations.service.repositories;

import com.example.bankingoperations.service.entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Integer> {
}
