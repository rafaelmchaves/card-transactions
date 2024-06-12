package com.pismo.transactions.controller;

import com.pismo.transactions.controller.requests.TransactionRequest;
import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequest transactionRequest) {

        transactionService.createTransaction(Transaction.builder().amount(transactionRequest.getAmount())
                .operationType(OperationType.builder().id(transactionRequest.getOperationTypeId()).build())
                        .account(Account.builder().id(transactionRequest.getAccountId()).build())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).build(); //TODO think about the return of the method. Should I return the id in the header or a body with the saved entity?
    }

}
