package com.example.bankingoperations.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoDTO {

    Integer id;
    LocalDate dateOfBirth;
    String phone;
    String lastName;
    String firstName;
    String middleName;
    String email;

}
