package com.example.bankingoperations.service.impl;

import com.example.bankingoperations.dto.UserTransferDTO;
import com.example.bankingoperations.service.entities.BankAccountEntity;
import com.example.bankingoperations.service.entities.UserEntity;
import com.example.bankingoperations.service.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransferMoneyImpl implements TransferMoneyService {

    private final UserRepository userRepository;

    public TransferMoneyImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public synchronized String transferMoney(UserTransferDTO userTransferDTO) {
        String senderLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        synchronized (this) {
            UserEntity senderUser = userRepository
                    .findByLogin(senderLogin)
                    .orElseThrow(() -> new IllegalArgumentException("Отправитель не найден"));
            log.error("Отправитель не найден");
            UserEntity recipientUser = userRepository
                    .findByLogin(userTransferDTO.getLogin())
                    .orElseThrow(() -> new IllegalArgumentException("Получатель не найден"));
            log.error("Получатель не найден");

            BankAccountEntity senderAccount = senderUser.getBankAccountEntity();
            BankAccountEntity recipientAccount = recipientUser.getBankAccountEntity();

            double amount = userTransferDTO.getAmount();

            if (hasEnoughBalance(senderAccount, amount)) {
                double newSenderBalance = senderAccount.getPercentageOfDeposit() - amount;
                senderAccount.setPercentageOfDeposit(newSenderBalance);
                recipientAccount.setPercentageOfDeposit(recipientAccount.getPercentageOfDeposit() + amount);

                userRepository.saveAndFlush(senderUser);

                return "Деньги перевели";

            } else {
                return "Ошибка: недостаточно средств на счете отправителя";

            }
        }
    }

    private boolean hasEnoughBalance(BankAccountEntity account, double amount) {
        return account.getPercentageOfDeposit() >= amount;
    }


}
