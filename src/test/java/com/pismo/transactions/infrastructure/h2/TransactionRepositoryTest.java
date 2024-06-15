package com.pismo.transactions.infrastructure.h2;

import com.pismo.transactions.adapter.infrastructure.h2.TransactionRepository;
import com.pismo.transactions.adapter.infrastructure.h2.entity.AccountJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.entity.OperationTypeJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.entity.TransactionJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.repository.TransactionJPARepository;
import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositoryTest {

    @Mock
    private TransactionJPARepository transactionJPARepository;

    @InjectMocks
    private TransactionRepository transactionRepository;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = Transaction.builder()
                .amount(BigDecimal.valueOf(10.56))
                .account(Account.builder().id(UUID.randomUUID().toString()).build())
                .operationType(OperationType.builder().id(1).multiplier(-1).description("COMPRA A VISTA").build())
                .build();


    }

    @Test
    public void createTransaction_givenTransaction_savedTransaction() {

        var savedTransactionJpaEntity = TransactionJpaEntity.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(10.56))
                .operationType(OperationTypeJpaEntity.builder().id(1).multiplier(-1).description("COMPRA A VISTA").build())
                .eventDate(LocalDateTime.now(ZoneOffset.UTC))
                .account(AccountJpaEntity.builder().id(UUID.fromString(transaction.getAccount().getId())).build())
                .build();

        when(transactionJPARepository.save(any(TransactionJpaEntity.class))).thenReturn(savedTransactionJpaEntity);

        Transaction savedTransaction = transactionRepository.createTransaction(transaction);

        final var argumentCaptor = ArgumentCaptor.forClass(TransactionJpaEntity.class);
        verify(transactionJPARepository, times(1)).save(argumentCaptor.capture());

        final var transactionJpaEntity = argumentCaptor.getValue();
        assertAll(() -> {
            assertNotNull(transactionJpaEntity);
            assertNotNull(transactionJpaEntity.getEventDate());
            assertNotNull(transactionJpaEntity.getOperationType());
            assertNotNull(transactionJpaEntity.getAccount());
            assertEquals(transaction.getAmount(), transactionJpaEntity.getAmount());
            assertEquals(transaction.getAccount().getId(), transactionJpaEntity.getAccount().getId().toString());
            assertEquals(transaction.getOperationType().getId(), transactionJpaEntity.getOperationType().getId());

            assertEquals(savedTransactionJpaEntity.getId(), savedTransaction.getId());
            assertEquals(savedTransactionJpaEntity.getAmount(), savedTransaction.getAmount());
            verify(transactionJPARepository, times(1)).save(transactionJpaEntity);
        });
    }
}
