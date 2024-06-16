package com.pismo.transactions.adapter.infrastructure.h2.mapper;

import com.pismo.transactions.adapter.infrastructure.h2.jpa.entity.OperationTypeJpaEntity;
import com.pismo.transactions.domain.OperationType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperationTypeMapper {

    OperationType convert(OperationTypeJpaEntity operationTypeJpaEntity);

}
