package com.pismo.transactions.infrastructure.jpa;

import com.pismo.transactions.infrastructure.entity.OperationTypeJpaEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OperationTypeRepository extends ListCrudRepository<OperationTypeJpaEntity, Integer> {
}
