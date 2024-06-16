package com.pismo.transactions.adapter.infrastructure.h2.jpa;

import com.pismo.transactions.adapter.infrastructure.h2.jpa.entity.TransactionJpaEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJPARepository extends ListCrudRepository<TransactionJpaEntity, Long> {


}
