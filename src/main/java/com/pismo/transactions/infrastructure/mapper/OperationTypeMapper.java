package com.pismo.transactions.infrastructure.mapper;

import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.infrastructure.entity.OperationTypeJpaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationTypeMapper {

    OperationType convert(OperationTypeJpaEntity operationTypeJpaEntity);

}
