package com.pismo.transactions.infrastructure.jpa;

import com.pismo.transactions.infrastructure.entity.OperationTypeJpaEntity;
import org.springframework.data.repository.CrudRepository;

public interface OperationTypeRepository extends CrudRepository<OperationTypeJpaEntity, Long> {
}
