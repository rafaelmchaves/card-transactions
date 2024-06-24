package com.pismo.transactions.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
public class Transaction {

    private Long id;

    @Setter
    private OperationType operationType;
    private BigDecimal amount;
    private Account account;

    @Setter
    private BigDecimal balance;

    public BigDecimal getAmount() {
        if (operationType != null) {
            return amount.multiply(BigDecimal.valueOf(operationType.getMultiplier()));
        }

        return amount;
    }

}
