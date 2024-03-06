package com.example.bankingoperations.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    Integer id;
    String login;
    String password;
    String phone;
    String email;
    LocalDate dateOfBirth;
    String lastName;
    String firstName;
    String middleName;
    Double amount;

}
