package com.example.bankingoperations.dto;

import com.example.bankingoperations.service.entities.BankAccountEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationUserDto {

    String login;
    String password;
    String confirmPassword;
    String lastName;
    String firstName;
    String middleName;
    LocalDate dateOfBirth;
    String email;
    String phone;


}
