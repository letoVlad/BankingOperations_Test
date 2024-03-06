package com.example.bankingoperations.controller;

import com.example.bankingoperations.service.UserService;
import com.example.bankingoperations.service.entities.UserEntity;
import com.example.bankingoperations.service.impl.SearchInfoServiceImpl;
import com.example.bankingoperations.specification.UserSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/search")
public class SearchInfoController {

    @Autowired
    private SearchInfoServiceImpl searchInfoService;



    @GetMapping("/users/by-dob")
    public Page<UserEntity> searchUsersByDob(
            @RequestParam String dob,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateOfBirth") String sortBy) {

        LocalDate dateOfBirth = LocalDate.parse(dob);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        return searchInfoService.searchUsersByDob(dateOfBirth, pageRequest);
    }

    @GetMapping("/users/by-phone")
    public Page<UserEntity> searchUsersByPhone(
            @RequestParam String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        return searchInfoService.searchUsersByPhone(phone, pageRequest);
    }

    @GetMapping("/users/by-name")
    public Page<UserEntity> searchUsersByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName,firstName,middleName") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy.split(",")));
        return searchInfoService.searchUsersByName(name, pageRequest);
    }

    @GetMapping("/users/by-email")
    public Page<UserEntity> searchUsersByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "email") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
        return searchInfoService.searchUsersByEmail(email, pageRequest);
    }

}
