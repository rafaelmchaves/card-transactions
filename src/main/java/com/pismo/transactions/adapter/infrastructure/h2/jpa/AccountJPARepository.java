package com.pismo.transactions.adapter.infrastructure.h2.jpa;

import com.pismo.transactions.adapter.infrastructure.h2.jpa.entity.AccountJpaEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface AccountJPARepository extends ListCrudRepository<AccountJpaEntity, UUID> {

    AccountJpaEntity findByDocumentNumber(String documentNumber);
}
