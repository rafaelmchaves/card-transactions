package com.pismo.transactions.domain.service;

import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.exceptions.AccountNotFoundException;
import com.pismo.transactions.domain.exceptions.OperationTypeNotFoundException;
import com.pismo.transactions.domain.ports.AccountPort;
import com.pismo.transactions.domain.ports.OperationTypePort;
import com.pismo.transactions.domain.ports.TransactionPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionPort transactionPort;

    private final AccountPort accountPort;

    private final OperationTypePort operationTypePort;

    @Transactional
    public void createTransaction(Transaction transaction) {

        accountPort.getAccount(UUID.fromString(transaction.getAccount().getId()))
                .orElseThrow(AccountNotFoundException::new);

        final var operationType = operationTypePort.getOperationTypeById(transaction.getOperationType().getId())
                .orElseThrow(OperationTypeNotFoundException::new);
        transaction.setOperationType(operationType);

        transactionPort.createTransaction(transaction);
    }
}
