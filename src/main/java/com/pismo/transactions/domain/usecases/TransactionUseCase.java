package com.pismo.transactions.domain.usecases;

import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.exceptions.AccountNotFoundException;
import com.pismo.transactions.domain.exceptions.OperationTypeNotFoundException;
import com.pismo.transactions.domain.ports.AccountPort;
import com.pismo.transactions.domain.ports.OperationTypePort;
import com.pismo.transactions.domain.ports.TransactionPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionUseCase {

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

        if (operationType.getMultiplier().equals(-1)) {
            transaction.setBalance(transaction.getAmount());
        } else {
            discharge(transaction);
        }

        transactionPort.createTransaction(transaction);
    }

    private void discharge(Transaction transaction) {
        final var transactions = transactionPort.findAllNegativeBalanceTransactions(transaction.getAccount().getId());
        BigDecimal value = transaction.getAmount();
        for (Transaction foundTransaction:transactions) {
            BigDecimal balance = BigDecimal.ZERO;
            if (value.compareTo(foundTransaction.getBalance().abs()) < 0) {
                balance = foundTransaction.getBalance().add(value);
                value = BigDecimal.ZERO;
            } else {
                value = value.subtract(foundTransaction.getBalance().abs());
            }

            foundTransaction.setBalance(balance);

            transactionPort.updateTransaction(foundTransaction);
            if (value.equals(BigDecimal.ZERO)) {
                break;
            }

        }
        transaction.setBalance(value);
    }
}
