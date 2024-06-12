package com.pismo.transactions.infrastructure;

import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.ports.AccountPort;
import com.pismo.transactions.infrastructure.entity.AccountJpaEntity;
import com.pismo.transactions.infrastructure.jpa.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AccountPortImpl implements AccountPort {

    private final AccountRepository accountRepository;

    @Override
    public void save(Account account) {
        final var accountJpaEntity = AccountJpaEntity.builder().documentNumber(account.getDocumentNumber())
                .creation(LocalDateTime.now(ZoneOffset.UTC))
                .build();
        this.accountRepository.save(accountJpaEntity);
    }

    @Override
    public Optional<Account> getAccount(Long id) {
        var account = accountRepository.findById(id).orElse(null);
        return account != null ? Optional.of(Account.builder().id(account.getId()).documentNumber(account.getDocumentNumber()).build()) :
                Optional.empty();
    }
}
