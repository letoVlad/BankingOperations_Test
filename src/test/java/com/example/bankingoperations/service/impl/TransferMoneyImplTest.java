package com.example.bankingoperations.service.impl;

import com.example.bankingoperations.dto.UserTransferDTO;
import com.example.bankingoperations.service.entities.BankAccountEntity;
import com.example.bankingoperations.service.entities.UserEntity;
import com.example.bankingoperations.service.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;

class TransferMoneyImplTest {

    private UserRepository userRepository;
    private TransferMoneyImpl transferMoney;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        transferMoney = new TransferMoneyImpl(userRepository);
        setupSecurityContext();
    }

    private void setupSecurityContext() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        UserDetails userDetails = new User("senderLogin", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testTransferMoney_Success() {
        UserEntity senderUser = new UserEntity();
        BankAccountEntity senderBankAccountEntity = new BankAccountEntity();

        // Инициализация банковского счета отправителя
        senderBankAccountEntity.setAmount(1000.00);
        senderBankAccountEntity.setPercentageOfDeposit(200.20);
        senderUser.setBankAccountEntity(senderBankAccountEntity);

        // Мокируем поведение userRepository для отправителя
        when(userRepository.findByLogin("senderLogin")).thenReturn(Optional.of(senderUser));

        UserEntity recipientUser = new UserEntity();
        BankAccountEntity recipientBankAccountEntity = new BankAccountEntity();
        // Инициализация банковского счета получателя
        recipientBankAccountEntity.setAmount(500.00);
        recipientBankAccountEntity.setPercentageOfDeposit(200.20);
        recipientUser.setBankAccountEntity(recipientBankAccountEntity);

        // Мокируем поведение userRepository для получателя
        when(userRepository.findByLogin("recipientLogin")).thenReturn(Optional.of(recipientUser));

        UserTransferDTO userTransferDTO = new UserTransferDTO();
        userTransferDTO.setLogin("recipientLogin");
        userTransferDTO.setAmount(100.2);

        String result = transferMoney.transferMoney(userTransferDTO);
        assertEquals("Деньги перевели", result);

        assertEquals(1000.0, senderUser.getBankAccountEntity().getAmount(), 0.01);
        assertEquals(500.0, recipientUser.getBankAccountEntity().getAmount(), 0.01);
    }
}


