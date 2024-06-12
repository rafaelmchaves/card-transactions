package com.pismo.transactions.domain.service;

import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.ports.AccountPort;
import com.pismo.transactions.domain.ports.TransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionPort transactionPort;

    private final AccountPort accountPort;

    public Transaction createTransaction(Transaction transaction) {

        accountPort.getAccount(UUID.fromString(transaction.getAccount().getId()))
                .orElseThrow(() -> new RuntimeException("It was not possible to find the account"));

        //TODO find the operational type, if not find, throw an exception

        //TODO multiply the amount of transaction for multiplier of OperationType
        transactionPort.createTransaction(transaction);
        return null;
    }
}
