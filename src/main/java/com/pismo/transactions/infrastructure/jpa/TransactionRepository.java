package com.pismo.transactions.infrastructure.jpa;

import com.pismo.transactions.infrastructure.entity.TransactionJpaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionJpaEntity, Long> {

}
