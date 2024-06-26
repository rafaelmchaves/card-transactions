package com.pismo.transactions.usecases;

import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.Transaction;
import com.pismo.transactions.domain.exceptions.AccountNotFoundException;
import com.pismo.transactions.domain.exceptions.OperationTypeNotFoundException;
import com.pismo.transactions.domain.ports.AccountPort;
import com.pismo.transactions.domain.ports.OperationTypePort;
import com.pismo.transactions.domain.ports.TransactionPort;
import com.pismo.transactions.domain.usecases.TransactionUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionUseCaseTest {

    @InjectMocks
    private TransactionUseCase transactionUseCase;

    @Mock
    private TransactionPort transactionPort;

    @Mock
    private AccountPort accountPort;

    @Mock
    private OperationTypePort operationTypePort;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = Transaction.builder().account(Account.builder().id(UUID.randomUUID().toString()).build())
                .amount(BigDecimal.valueOf(50))
                .operationType(OperationType.builder().id(1).build()).build();
    }

    @Test
    void createTransaction_accountAndOperationTypeFound_transactionCreated() {

        Mockito.when(accountPort.getAccount(UUID.fromString(transaction.getAccount().getId())))
                .thenReturn(getAccount());

        Mockito.when(operationTypePort.getOperationTypeById(transaction.getOperationType().getId()))
                .thenReturn(getOperationType());

        transactionUseCase.createTransaction(transaction);

        final var transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);

        Mockito.verify(transactionPort, Mockito.times(1)).createTransaction(transactionArgumentCaptor.capture());
        var transactionParam = transactionArgumentCaptor.getValue();

        assertAll(() -> {
            Assertions.assertEquals(transaction.getAmount(), transactionParam.getAmount());
            Assertions.assertEquals(1, transactionParam.getOperationType().getMultiplier());
            Assertions.assertEquals(4, transactionParam.getOperationType().getId());
            Assertions.assertEquals(transaction.getAccount().getId(), transactionParam.getAccount().getId());
        });

    }

    @Test
    void createTransaction_accountAndOperationTypeEquals4_transactionCreated() {

        transaction = Transaction.builder().account(Account.builder().id(UUID.randomUUID().toString()).build())
                .amount(BigDecimal.valueOf(200))
                .operationType(OperationType.builder().multiplier(1).id(4).build()).build();

        Mockito.when(accountPort.getAccount(UUID.fromString(transaction.getAccount().getId())))
                .thenReturn(getAccount());

        List <Transaction> transactions = new ArrayList<>();
        transactions.add(Transaction.builder().id(1L).amount(BigDecimal.valueOf(-10)).balance(BigDecimal.valueOf(-10)).build());

        Mockito.when(transactionPort.findAllNegativeBalanceTransactions(transaction.getAccount().getId()))
                .thenReturn(transactions);

        Mockito.when(operationTypePort.getOperationTypeById(transaction.getOperationType().getId()))
                .thenReturn(getOperationType());

        transactionUseCase.createTransaction(transaction);

        final var transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);

        Mockito.verify(transactionPort, Mockito.times(1)).createTransaction(transactionArgumentCaptor.capture());
        var transactionParam = transactionArgumentCaptor.getValue();

        final var foundTransactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(transactionPort, Mockito.times(1)).updateTransaction(foundTransactionArgumentCaptor.capture());
        var updatedTransaction = foundTransactionArgumentCaptor.getValue();

        assertAll(() -> {
            Assertions.assertEquals(transaction.getAmount(), transactionParam.getAmount());
            Assertions.assertEquals(1, transactionParam.getOperationType().getMultiplier());
            Assertions.assertEquals(4, transactionParam.getOperationType().getId());
            Assertions.assertEquals(BigDecimal.valueOf(190), transactionParam.getBalance());
            Assertions.assertEquals(BigDecimal.valueOf(0), updatedTransaction.getBalance());
            Assertions.assertEquals(transaction.getAccount().getId(), transactionParam.getAccount().getId());
        });

    }

    @Test
    void createTransaction_accountNotFound_exceptionsIsThrown() {

        when(accountPort.getAccount(UUID.fromString(transaction.getAccount().getId()))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> transactionUseCase.createTransaction(transaction));

        assertAll(() -> {
            verify(accountPort, times(1)).getAccount(UUID.fromString(transaction.getAccount().getId()));
            verify(operationTypePort, never()).getOperationTypeById(transaction.getOperationType().getId());
            verify(transactionPort, never()).createTransaction(any(Transaction.class));
        });
    }

    @Test
    void createTransaction_OperationTypeNotFound_exceptionsIsThrown() {
        when(accountPort.getAccount(UUID.fromString(transaction.getAccount().getId()))).thenReturn(getAccount());
        when(operationTypePort.getOperationTypeById(transaction.getOperationType().getId())).thenReturn(Optional.empty());

        assertThrows(OperationTypeNotFoundException.class, () -> transactionUseCase.createTransaction(transaction));

        verify(accountPort, times(1)).getAccount(UUID.fromString(transaction.getAccount().getId()));
        verify(operationTypePort, times(1)).getOperationTypeById(transaction.getOperationType().getId());
        verify(transactionPort, never()).createTransaction(any(Transaction.class));
    }

    private static Optional<OperationType> getOperationType() {
        return Optional.of(OperationType.builder().id(4).multiplier(1).description("PAGAMENTO").build());
    }

    private Optional<Account> getAccount() {
        return Optional.of(Account.builder().id(transaction.getAccount().getId()).build());
    }

}
