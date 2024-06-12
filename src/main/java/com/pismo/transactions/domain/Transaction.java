package com.pismo.transactions.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class Transaction {

    private Long id;
    private OperationType operationType;
    private BigDecimal amount;
    private Account account;

}
