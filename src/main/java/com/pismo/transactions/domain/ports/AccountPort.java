package com.pismo.transactions.domain.ports;

import com.pismo.transactions.domain.Account;

public interface AccountPort {

    void save(Account account);

    Account getAccount(Long id);
}
