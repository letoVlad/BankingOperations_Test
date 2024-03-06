package com.example.bankingoperations.service.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "bank_accounts")
@JsonIgnoreProperties({"user"}) // Игнорируем обратную ссылку в классе BankAccountEntity
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

    @Column(name = "amount")
    Double amount;

    @Column(name = "percentage_of_deposit")
    Double percentageOfDeposit;

}
