package com.pismo.transactions.domain.ports;

import com.pismo.transactions.domain.OperationType;

import java.util.Optional;

public interface OperationTypePort {

    Optional<OperationType> getOperationTypeById(Integer id);
}
