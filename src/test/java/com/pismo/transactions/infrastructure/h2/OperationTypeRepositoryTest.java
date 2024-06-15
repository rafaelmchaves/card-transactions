package com.pismo.transactions.infrastructure.h2;

import com.pismo.transactions.adapter.infrastructure.h2.OperationTypeRepository;
import com.pismo.transactions.adapter.infrastructure.h2.entity.OperationTypeJpaEntity;
import com.pismo.transactions.adapter.infrastructure.h2.mapper.OperationTypeMapper;
import com.pismo.transactions.adapter.infrastructure.h2.repository.OperationTypeJPARepository;
import com.pismo.transactions.domain.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationTypeRepositoryTest {

    @Mock
    private OperationTypeJPARepository operationTypeJPARepository;

    @Mock
    private OperationTypeMapper operationTypeMapper;

    @InjectMocks
    private OperationTypeRepository operationTypeRepository;

    private OperationType operationType;

    @BeforeEach
    public void setUp() {
        operationType = OperationType.builder().id(1).description("COMPRA A VISTA").multiplier(-1).build();
    }

    @Test
    public void getOperationTypeById_givenOperationTypeId_operationTypeWasFound() {

        OperationTypeJpaEntity operationTypeJpaEntity = OperationTypeJpaEntity.builder().id(1).description("COMPRA A VISTA").multiplier(-1).build();
        when(operationTypeJPARepository.findById(operationType.getId())).thenReturn(Optional.of(operationTypeJpaEntity));

        when(operationTypeMapper.convert(operationTypeJpaEntity)).thenReturn(operationType);

        Optional<OperationType> foundOperationType = operationTypeRepository.getOperationTypeById(1);

        assertTrue(foundOperationType.isPresent());
        assertEquals(operationType.getId(), foundOperationType.get().getId());
        assertEquals(operationType.getDescription(), foundOperationType.get().getDescription());
        verify(operationTypeJPARepository, times(1)).findById(operationType.getId());
        verify(operationTypeMapper, times(1)).convert(any(OperationTypeJpaEntity.class));
    }

    @Test
    public void getOperationTypeById_givenOperationTypeId_operationTypeNotFound() {
        when(operationTypeJPARepository.findById(operationType.getId())).thenReturn(Optional.empty());

        Optional<OperationType> foundOperationType = operationTypeRepository.getOperationTypeById(1);

        assertTrue(foundOperationType.isEmpty());
        verify(operationTypeJPARepository, times(1)).findById(operationType.getId());
        verify(operationTypeMapper, never()).convert(any(OperationTypeJpaEntity.class));
    }
}
