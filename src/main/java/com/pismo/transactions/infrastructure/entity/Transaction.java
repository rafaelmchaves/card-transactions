package com.pismo.transactions.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@Table(name = "transactions")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AccountJpaEntity account;

    @ManyToOne
    private OperationType operationType;

    private BigDecimal amount;
    private LocalDateTime eventDate;

}
