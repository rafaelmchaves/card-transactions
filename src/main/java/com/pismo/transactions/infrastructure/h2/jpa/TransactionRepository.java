package com.pismo.transactions.infrastructure.h2.jpa;

import com.pismo.transactions.infrastructure.h2.entity.TransactionJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionJpaEntity, Long> {

}
