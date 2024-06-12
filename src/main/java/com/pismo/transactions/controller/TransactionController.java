package com.pismo.transactions.controller;

import com.pismo.transactions.controller.requests.TransactionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @PostMapping("/transactions")
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequest transactionRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).build(); //TODO think about the return of the method. Should I return the id in the header or a body with the saved entity?
    }

}
