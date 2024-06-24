package com.pismo.transactions.adapter.infrastructure.h2;

import com.pismo.transactions.adapter.infrastructure.h2.jpa.entity.AccountJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.jpa.entity.OperationTypeJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.jpa.entity.TransactionJpaEntity;
import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.ports.TransactionPort;
import com.pismo.transactions.adapter.infrastructure.h2.jpa.TransactionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TransactionRepository implements TransactionPort {

    private final TransactionJPARepository transactionJPARepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        final var savedTransaction = transactionJPARepository.save(buildTransactionJpaEntity(transaction));

        return Transaction.builder().id(savedTransaction.getId()).amount(savedTransaction.getAmount())
                .build();
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        final var transactionEntity = buildTransactionJpaEntity(transaction);
        transactionEntity.setId(transaction.getId());
        transactionJPARepository.save(transactionEntity);
    }

    @Override
    public List<Transaction> findAllNegativeBalanceTransactions(String accountId) {
        final var foundTransactions = transactionJPARepository.findAllByAccountIdSortByEventDate(UUID.fromString(accountId));

        return foundTransactions.stream().map(transactionJpaEntity -> Transaction.builder().balance(transactionJpaEntity.getBalance())
                .amount(transactionJpaEntity.getAmount()).id(transactionJpaEntity.getId())
                .operationType(
                        OperationType.builder().multiplier(transactionJpaEntity.getOperationType().getMultiplier())
                                .id(transactionJpaEntity.getOperationType().getId())
                                .description(transactionJpaEntity.getOperationType().getDescription())
                        .build())
                .account(Account.builder().documentNumber(transactionJpaEntity.getAccount().getDocumentNumber())
                        .id(transactionJpaEntity.getAccount().getId().toString()).build())
                .build()
        ).toList();
    }

    private static TransactionJpaEntity buildTransactionJpaEntity(Transaction transaction) {
        return TransactionJpaEntity.builder().amount(transaction.getAmount())
                .operationType(
                        OperationTypeJpaEntity.builder().id(transaction.getOperationType().getId()).build())
                .balance(transaction.getBalance())
                .eventDate(LocalDateTime.now(ZoneOffset.UTC))
                .account(AccountJpaEntity.builder().id(UUID.fromString(transaction.getAccount().getId())).build())
                .build();
    }
}
