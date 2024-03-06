package com.example.bankingoperations.controller;

import com.example.bankingoperations.dto.UserTransferDTO;
import com.example.bankingoperations.service.impl.TransferMoneyImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    @Autowired
    private TransferMoneyImpl transferMoney;

    @Operation(summary = "Перевод денег")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Деньги перевели")
    })
    @PatchMapping("/money")
    public ResponseEntity<String> transferMoney(@RequestBody UserTransferDTO userTransferDTO) {
        var answerTransfer = transferMoney.transferMoney(userTransferDTO);
        return ResponseEntity.ok(answerTransfer);
    }
}
