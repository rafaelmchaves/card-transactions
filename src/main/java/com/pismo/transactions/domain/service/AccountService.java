package com.pismo.transactions.domain.service;

import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.ports.AccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountPort accountPort;

    public Account createAccount(Account account) {
        final String accountId = this.accountPort.save(account);
        account.setId(accountId);
        return account;
    }

    public Account getAccount(String id) {
        return accountPort.getAccount(UUID.fromString(id)).orElse(Account.builder().build());
    }
}
