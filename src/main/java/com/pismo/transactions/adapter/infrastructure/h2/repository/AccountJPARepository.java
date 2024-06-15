package com.pismo.transactions.adapter.infrastructure.h2.repository;

import com.pismo.transactions.adapter.infrastructure.h2.entity.AccountJpaEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface AccountJPARepository extends ListCrudRepository<AccountJpaEntity, UUID> {

}
