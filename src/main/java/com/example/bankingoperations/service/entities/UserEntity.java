package com.example.bankingoperations.service.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"userPhoneEntityList", "userEmailEntityList", "bankAccountEntity"}) // Игнорируем обратные ссылки в классе UserEntity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NonNull
    @Column(name = "password")
    String password;

    @Column(name = "date_of_birth")
    LocalDate dateOfBirth;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "middle_name")
    String middleName;

    @Column(name = "login")
    String login;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id")
    BankAccountEntity bankAccountEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserPhoneEntity> userPhoneEntityList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<UserEmailEntity> userEmailEntityList = new ArrayList<>();
}
