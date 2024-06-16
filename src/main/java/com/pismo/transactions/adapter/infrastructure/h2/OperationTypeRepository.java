package com.pismo.transactions.adapter.infrastructure.h2;

import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.ports.OperationTypePort;
import com.pismo.transactions.adapter.infrastructure.h2.jpa.OperationTypeJPARepository;
import com.pismo.transactions.adapter.infrastructure.h2.mapper.OperationTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OperationTypeRepository implements OperationTypePort {

    private final OperationTypeJPARepository operationTypeJPARepository;

    private final OperationTypeMapper operationTypeMapper;

    @Override
    @Cacheable(value = "operationType")
    public Optional<OperationType> getOperationTypeById(Integer id) {
        final var operationType = operationTypeJPARepository.findById(id).orElse(null);
        return Optional.ofNullable(operationTypeMapper.convert(operationType));
    }
}
