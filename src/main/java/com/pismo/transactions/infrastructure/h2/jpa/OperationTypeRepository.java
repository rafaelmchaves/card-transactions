package com.pismo.transactions.infrastructure.h2.jpa;

import com.pismo.transactions.infrastructure.h2.entity.OperationTypeJpaEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OperationTypeRepository extends ListCrudRepository<OperationTypeJpaEntity, Integer> {
}
