package com.pismo.transactions.domain.ports;

import com.pismo.transactions.domain.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountPort {

    String save(Account account);

    Optional<Account> getAccount(UUID id);

    Optional<Account> getAccountByDocumentNumber(String documentNumber);
}
