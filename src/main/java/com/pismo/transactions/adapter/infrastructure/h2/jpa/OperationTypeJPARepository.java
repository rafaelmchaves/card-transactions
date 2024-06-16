package com.pismo.transactions.adapter.infrastructure.h2.jpa;

import com.pismo.transactions.adapter.infrastructure.h2.jpa.entity.OperationTypeJpaEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface OperationTypeJPARepository extends ListCrudRepository<OperationTypeJpaEntity, Integer> {
}
