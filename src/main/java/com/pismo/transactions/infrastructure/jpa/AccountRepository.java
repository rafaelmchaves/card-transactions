package com.pismo.transactions.infrastructure.jpa;

import com.pismo.transactions.infrastructure.entity.AccountJpaEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AccountRepository extends CrudRepository<AccountJpaEntity, UUID> {

}
