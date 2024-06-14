package com.pismo.transactions.adapter.infrastructure.h2.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@Table(name = "transactions")
@Entity
public class TransactionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AccountJpaEntity account;

    @ManyToOne
    private OperationTypeJpaEntity operationType;

    private BigDecimal amount;
    private LocalDateTime eventDate;

}
