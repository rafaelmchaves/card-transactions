package com.pismo.transactions.infrastructure;

import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.ports.TransactionPort;
import com.pismo.transactions.infrastructure.entity.AccountJpaEntity;
import com.pismo.transactions.infrastructure.entity.OperationTypeJpaEntity;
import com.pismo.transactions.infrastructure.entity.TransactionJpaEntity;
import com.pismo.transactions.infrastructure.jpa.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Component
public class TransactionPortImpl implements TransactionPort {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transactionRepository.save(TransactionJpaEntity.builder().amount(transaction.getAmount())
                        .operationTypeJpaEntity(
                                OperationTypeJpaEntity.builder().id(transaction.getOperationType().getId()).build())
                        .eventDate(LocalDateTime.now(ZoneOffset.UTC))
                        .account(AccountJpaEntity.builder().id(transaction.getAccount().getId()).build())
                        .build());
        return null;
    }
}
