package com.pismo.transactions.adapter.controller;

import com.pismo.transactions.adapter.controller.requests.TransactionRequest;
import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Transaction", description = "Transaction APIs")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    @Operation(summary = "Create a new transaction for a particular account.")
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {

        transactionService.createTransaction(Transaction.builder().amount(transactionRequest.getAmount())
                .operationType(OperationType.builder().id(transactionRequest.getOperationTypeId()).build())
                        .account(Account.builder().id(transactionRequest.getAccountId()).build())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).build(); //TODO think about the return of the method. Should I return the id in the header or a body with the saved entity?
    }

}
