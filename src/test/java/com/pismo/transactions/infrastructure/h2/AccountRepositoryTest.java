package com.pismo.transactions.infrastructure.h2;

import com.pismo.transactions.adapter.infrastructure.h2.AccountRepository;
import com.pismo.transactions.adapter.infrastructure.h2.entity.AccountJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.repository.AccountJPARepository;
import com.pismo.transactions.domain.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryTest {

    @Mock
    private AccountJPARepository accountJPARepository;

    @InjectMocks
    private AccountRepository accountRepository;

    private Account account;
    private AccountJpaEntity accountJpaEntity;
    private UUID accountId;

    @BeforeEach
    public void setUp() {
        accountId = UUID.randomUUID();
        account = Account.builder()
                .id(accountId.toString())
                .documentNumber("21223131")
                .build();

        accountJpaEntity = AccountJpaEntity.builder()
                .id(accountId)
                .documentNumber("21223131")
                .creation(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }

    @Test
    public void save_givenAccount_accountSaved() {
        when(accountJPARepository.save(any(AccountJpaEntity.class))).thenReturn(accountJpaEntity);

        String savedAccountId = accountRepository.save(account);

        ArgumentCaptor<AccountJpaEntity> argumentCaptor = ArgumentCaptor.forClass(AccountJpaEntity.class);
        verify(accountJPARepository, times(1)).save(argumentCaptor.capture());

        AccountJpaEntity capturedAccountJpaEntity = argumentCaptor.getValue();

        assertAll(() -> {
            assertNotNull(capturedAccountJpaEntity);
            assertNotNull(capturedAccountJpaEntity.getCreation());
            assertEquals(account.getDocumentNumber(), capturedAccountJpaEntity.getDocumentNumber());
            assertEquals(accountId.toString(), savedAccountId);
        });

    }

    @Test
    public void getAccount_givenAccountId_accountFound() {
        when(accountJPARepository.findById(accountId)).thenReturn(Optional.of(accountJpaEntity));

        Optional<Account> foundAccount = accountRepository.getAccount(accountId);

        assertAll(() -> {
            assertTrue(foundAccount.isPresent());
            assertEquals(accountId.toString(), foundAccount.get().getId());
            assertEquals(account.getDocumentNumber(), foundAccount.get().getDocumentNumber());
            verify(accountJPARepository, times(1)).findById(any(UUID.class));
        });
    }

    @Test
    public void getAccount_givenNotExistAccountId_accountNotFound() {
        when(accountJPARepository.findById(accountId)).thenReturn(Optional.empty());

        Optional<Account> foundAccount = accountRepository.getAccount(accountId);

        assertTrue(foundAccount.isEmpty());
        verify(accountJPARepository, times(1)).findById(any(UUID.class));
    }
}
