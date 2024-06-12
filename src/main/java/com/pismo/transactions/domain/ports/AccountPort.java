package com.pismo.transactions.domain.ports;

import com.pismo.transactions.domain.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountPort {

    void save(Account account);

    Optional<Account> getAccount(UUID id);
}
