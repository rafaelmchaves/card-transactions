package com.pismo.transactions.adapter.infrastructure.h2.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Table(name = "operation_types")
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OperationTypeJpaEntity {

    @Id
    private Integer id;
    private String description;
    private Integer multiplier;

}
