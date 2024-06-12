package com.pismo.transactions.infrastructure.jpa;

import com.pismo.transactions.infrastructure.entity.AccountJpaEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<AccountJpaEntity, Long> {

}
