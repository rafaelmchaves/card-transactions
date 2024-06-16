package com.pismo.transactions.adapter.infrastructure.h2.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@Table(name = "transactions")
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
