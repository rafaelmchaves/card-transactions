package com.pismo.transactions.service;

import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.ports.AccountPort;
import com.pismo.transactions.domain.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountPort accountPort;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = Account.builder().id(UUID.randomUUID().toString()).documentNumber("21242114").build();
    }

    @Test
    public void createAccount_accountData_accountCreated() {
        when(accountPort.save(account)).thenReturn(account.getId());

        Account createdAccount = accountService.createAccount(account);

        assertEquals(account.getId(), createdAccount.getId());
        verify(accountPort, times(1)).save(account);
    }

    @Test
    public void getAccount_fromExistedId_accountReturned() {

        UUID accountId = UUID.fromString(account.getId());
        when(accountPort.getAccount(accountId)).thenReturn(Optional.of(account));

        Optional<Account> foundAccount = accountService.getAccount(account.getId());

        assertTrue(foundAccount.isPresent());
        assertEquals(account.getId(), foundAccount.get().getId());
        assertEquals(account.getDocumentNumber(), foundAccount.get().getDocumentNumber());
        verify(accountPort, times(1)).getAccount(accountId);
    }

    @Test
    public void getAccount_fromNotExistedId_emptyOptionalReturned() {
        UUID accountId = UUID.fromString(account.getId());
        when(accountPort.getAccount(accountId)).thenReturn(Optional.empty());

        Optional<Account> foundAccount = accountService.getAccount(account.getId());

        assertTrue(foundAccount.isEmpty());
        verify(accountPort, times(1)).getAccount(accountId);
    }
}
