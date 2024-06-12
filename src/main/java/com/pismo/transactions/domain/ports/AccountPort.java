package com.pismo.transactions.domain.ports;

import com.pismo.transactions.domain.Account;

import java.util.Optional;

public interface AccountPort {

    void save(Account account);

    Optional<Account> getAccount(Long id);
}
