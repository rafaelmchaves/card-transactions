package com.pismo.transactions.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Table(name = "operation_types")
@Entity
public class OperationType {

    @Id
    private Long operationId;
    private String description;
    //TODO talvez criar uma coluna dizendo se é para debitar ou creditar

}
