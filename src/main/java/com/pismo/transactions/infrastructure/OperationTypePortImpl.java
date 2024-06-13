package com.pismo.transactions.infrastructure;

import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.ports.OperationTypePort;
import com.pismo.transactions.infrastructure.jpa.OperationTypeRepository;
import com.pismo.transactions.infrastructure.mapper.OperationTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OperationTypePortImpl implements OperationTypePort {

    private final OperationTypeRepository operationTypeRepository;

    private final OperationTypeMapper operationTypeMapper;

    @Override
    public Optional<OperationType> getOperationTypeById(Integer id) {
        final var operationType = operationTypeRepository.findById(id).orElse(null);
        return Optional.ofNullable(operationTypeMapper.convert(operationType));
    }
}
