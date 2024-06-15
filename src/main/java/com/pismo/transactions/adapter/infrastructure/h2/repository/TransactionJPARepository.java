package com.pismo.transactions.adapter.infrastructure.h2.repository;

import com.pismo.transactions.adapter.infrastructure.h2.entity.TransactionJpaEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJPARepository extends ListCrudRepository<TransactionJpaEntity, Long> {


}
