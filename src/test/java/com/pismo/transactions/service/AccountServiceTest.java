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
        account = Account.builder().id(UUID.randomUUID().toString()).build();
    }

    @Test
    public void testCreateAccount() {
        when(accountPort.save(account)).thenReturn(account.getId());

        Account createdAccount = accountService.createAccount(account);

        assertEquals(account.getId(), createdAccount.getId());
        verify(accountPort, times(1)).save(account);
    }

    @Test
    public void testGetAccount() {

        UUID accountId = UUID.fromString(account.getId());
        when(accountPort.getAccount(accountId)).thenReturn(Optional.of(account));

        Optional<Account> foundAccount = accountService.getAccount(account.getId());

        assertTrue(foundAccount.isPresent());
        assertEquals(account.getId(), foundAccount.get().getId());
        verify(accountPort, times(1)).getAccount(accountId);
    }

    @Test
    public void testGetAccount_NotFound() {
        UUID accountId = UUID.fromString(account.getId());
        when(accountPort.getAccount(accountId)).thenReturn(Optional.empty());

        Optional<Account> foundAccount = accountService.getAccount(account.getId());

        assertTrue(foundAccount.isEmpty());
        verify(accountPort, times(1)).getAccount(accountId);
    }
}
