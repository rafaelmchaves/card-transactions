package com.pismo.transactions.infrastructure.h2.repository;

import com.pismo.transactions.infrastructure.h2.entity.OperationTypeJpaEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OperationTypeJPARepository extends ListCrudRepository<OperationTypeJpaEntity, Integer> {
}
