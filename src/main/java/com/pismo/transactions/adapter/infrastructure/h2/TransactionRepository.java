package com.pismo.transactions.adapter.infrastructure.h2;

import com.pismo.transactions.adapter.infrastructure.h2.entity.AccountJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.entity.OperationTypeJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.entity.TransactionJpaEntity;
import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.ports.TransactionPort;
import com.pismo.transactions.adapter.infrastructure.h2.repository.TransactionJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TransactionRepository implements TransactionPort {

    private final TransactionJPARepository transactionJPARepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        final var savedTransaction = transactionJPARepository.save(TransactionJpaEntity.builder().amount(transaction.getAmount())
                        .operationType(
                                OperationTypeJpaEntity.builder().id(transaction.getOperationType().getId()).build())
                        .eventDate(LocalDateTime.now(ZoneOffset.UTC))
                        .account(AccountJpaEntity.builder().id(UUID.fromString(transaction.getAccount().getId())).build())
                        .build());

        return Transaction.builder().id(savedTransaction.getId()).amount(savedTransaction.getAmount())
                .build();
    }
}