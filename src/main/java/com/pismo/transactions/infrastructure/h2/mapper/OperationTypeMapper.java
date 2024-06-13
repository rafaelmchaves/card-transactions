package com.pismo.transactions.infrastructure.h2.mapper;

import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.infrastructure.h2.entity.OperationTypeJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperationTypeMapper {

    OperationType convert(OperationTypeJpaEntity operationTypeJpaEntity);

}
