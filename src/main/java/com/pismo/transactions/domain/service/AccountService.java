package com.pismo.transactions.domain.service;

import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.ports.AccountPort;
import com.pismo.transactions.infrastructure.entity.AccountJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountPort accountPort;

    public void createAccount(Account account) {
        this.accountPort.save(account);
    }

    public Account getAccount(Long id) {
        return accountPort.getAccount(id);
    }
}
