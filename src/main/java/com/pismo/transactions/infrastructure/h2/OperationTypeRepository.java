package com.pismo.transactions.infrastructure.h2;

import com.pismo.transactions.domain.OperationType;
import com.pismo.transactions.domain.ports.OperationTypePort;
import com.pismo.transactions.infrastructure.h2.repository.OperationTypeJPARepository;
import com.pismo.transactions.infrastructure.h2.mapper.OperationTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OperationTypeRepository implements OperationTypePort {

    private final OperationTypeJPARepository operationTypeJPARepository;

    private final OperationTypeMapper operationTypeMapper;

    @Override
    public Optional<OperationType> getOperationTypeById(Integer id) {
        final var operationType = operationTypeJPARepository.findById(id).orElse(null);
        return Optional.ofNullable(operationTypeMapper.convert(operationType));
    }
}
