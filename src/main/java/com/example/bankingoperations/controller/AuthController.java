package com.example.bankingoperations.controller;

import com.example.bankingoperations.dto.JwtRequestDTO;
import com.example.bankingoperations.dto.JwtResponseDTO;
import com.example.bankingoperations.exceptions.AppError;
import com.example.bankingoperations.service.impl.UserServiceImpl;
import com.example.bankingoperations.utils.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Получить JSON Web Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDTO authRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(),
                    "Неправильный логин или пароль."),
                    HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }
}
