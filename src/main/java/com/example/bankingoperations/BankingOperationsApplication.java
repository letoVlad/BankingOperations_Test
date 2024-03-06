package com.example.bankingoperations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankingOperationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingOperationsApplication.class, args);
    }

}
