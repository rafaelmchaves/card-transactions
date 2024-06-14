package com.pismo.transactions.infrastructure.h2;

import com.pismo.transactions.domain.Account;
import com.pismo.transactions.domain.ports.AccountPort;
import com.pismo.transactions.infrastructure.h2.entity.AccountJpaEntity;
import com.pismo.transactions.infrastructure.h2.repository.AccountJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AccountRepository implements AccountPort {

    private final AccountJPARepository accountJPARepository;

    @Override
    public String save(Account account) {
        final var accountJpaEntity = AccountJpaEntity.builder().documentNumber(account.getDocumentNumber())
                .creation(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        final var savedAccount = this.accountJPARepository.save(accountJpaEntity);

        return savedAccount.getId().toString();
    }

    @Override
    public Optional<Account> getAccount(UUID id) {
        var account = accountJPARepository.findById(id).orElse(null);
        return account != null ? Optional.of(Account.builder().id(account.getId().toString()).documentNumber(account.getDocumentNumber()).build())
                               : Optional.empty();
    }
}
