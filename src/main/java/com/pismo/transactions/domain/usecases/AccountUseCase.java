package com.pismo.transactions.domain.usecases;

import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.exceptions.DuplicatedDocumentNumberException;
import com.pismo.transactions.domain.ports.AccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountUseCase {

    private final AccountPort accountPort;

    public Account createAccount(Account account) {
        if (accountPort.getAccountByDocumentNumber(account.getDocumentNumber()).isPresent()) {
            throw new DuplicatedDocumentNumberException();
        }

        final String accountId = this.accountPort.save(account);
        account.setId(accountId);
        return account;
    }

    public Optional<Account> getAccount(String id) {
        return accountPort.getAccount(UUID.fromString(id));
    }
}
