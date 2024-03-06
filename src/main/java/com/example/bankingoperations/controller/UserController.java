package com.example.bankingoperations.controller;

import com.example.bankingoperations.dto.*;
import com.example.bankingoperations.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Добавить нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Добавить номер телефона")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping("/add_phone")
    public ResponseEntity<?> addPhone(@RequestBody AddUserPhoneDTO addUserPhoneDTO) {
        userService.addPhoneUser(addUserPhoneDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Добавить новый email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping("/add_email")
    public ResponseEntity<?> addEmail(@RequestBody AddUserEmailDTO addUserEmailDTO) {
        userService.addEmailUser(addUserEmailDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/delete_email")
    public ResponseEntity<?> deleteEmail(@RequestBody DeleteUserEmailDTO deleteUserEmailDTO) {
        userService.deleteUserEmail(deleteUserEmailDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить номер телефона")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/delete_phone")
    public ResponseEntity<?> deletePhone(@RequestBody DeleteUserPhoneDTO deleteUserPhoneDTO) {
        userService.deleteUserPhone(deleteUserPhoneDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PatchMapping("/update_user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDTO updateUserDTO) {
        userService.updateUser(updateUserDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление номер телефона пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PatchMapping("/update_phone")
    public ResponseEntity<?> updateUserPhone(@RequestBody UpdateUserPhoneDTO updateUserPhoneDTO) {
        userService.updateUserPhone(updateUserPhoneDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление email пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PatchMapping("/update_email")
    public ResponseEntity<?> updateUserEmail(@RequestBody UpdateUserEmailDTO updateUserEmailDTO) {
        userService.updateUserEmail(updateUserEmailDTO);
        return ResponseEntity.ok().build();
    }

}
