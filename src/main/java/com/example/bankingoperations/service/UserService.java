package com.example.bankingoperations.service;

import com.example.bankingoperations.dto.*;
import com.example.bankingoperations.service.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface UserService {

    void addUser(UserDTO userDTO);

    void updateUser(UpdateUserDTO updateUserDTO);

    void addEmailUser(AddUserEmailDTO addUserEmailDTO);

    void addPhoneUser(AddUserPhoneDTO addUserPhoneDTO);

    void deleteUserEmail(DeleteUserEmailDTO deleteUserEmailDTO);

    void deleteUserPhone(DeleteUserPhoneDTO deleteUserPhoneDTO);

    void updateUserPhone(UpdateUserPhoneDTO updateUserPhoneDTO);

    void updateUserEmail(UpdateUserEmailDTO updateUserEmailDTO);

}
