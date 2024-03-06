package com.example.bankingoperations.finance;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.bankingoperations.service.entities.BankAccountEntity;
import com.example.bankingoperations.service.repositories.BankAccountRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class Management {
    private final BankAccountRepository bankAccountRepository;

    public Management(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 60000) // Раз в минуту
    public void increaseBalances() {
        List<BankAccountEntity> bankAccounts = bankAccountRepository.findAll();
        for (BankAccountEntity account : bankAccounts) {
            synchronizeAccount(account);
        }
    }

    private synchronized void synchronizeAccount(BankAccountEntity account) {
        if (account.getPercentageOfDeposit() == null) {
            account.setPercentageOfDeposit(account.getAmount());
        }

        double currentAmount = account.getAmount();
        double maxDeposit = currentAmount * 2.07;

        double currentPercentage = account.getPercentageOfDeposit();
        double newPercentage = currentPercentage * 1.05;
        if (newPercentage <= maxDeposit) {
            account.setPercentageOfDeposit(newPercentage);
        }
        log.info("Процент по вкладу увеличился у пользователя " + account.getUser().getLogin());
        bankAccountRepository.saveAndFlush(account);
    }

}
