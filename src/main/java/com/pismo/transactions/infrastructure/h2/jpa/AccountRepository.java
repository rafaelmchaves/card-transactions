package com.pismo.transactions.infrastructure.h2.jpa;

import com.pismo.transactions.infrastructure.h2.entity.AccountJpaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AccountRepository extends CrudRepository<AccountJpaEntity, UUID> {

}
