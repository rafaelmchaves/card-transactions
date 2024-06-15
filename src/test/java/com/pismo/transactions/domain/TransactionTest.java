package com.pismo.transactions.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TransactionTest {

    @Test
    void getAmount_operationTypeIsNull_returnAmount() {
        BigDecimal amount = BigDecimal.valueOf(40.35);
        Transaction transaction = Transaction.builder().amount(amount).account(Account.builder().build()).build();

        BigDecimal resultedAmount = transaction.getAmount();

        Assertions.assertEquals(amount, resultedAmount);
    }

    @Test
    void getAmount_operationTypeHasMultiplierEqualsNegativeOne_returnNegativeAmount() {
        BigDecimal amount = BigDecimal.valueOf(40.35);
        OperationType operationType = OperationType.builder().id(1).multiplier(-1).description("COMPRA A VISTA").build();
        Transaction transaction = Transaction.builder().amount(amount).account(Account.builder().build())
                .operationType(operationType).build();

        BigDecimal resultedAmount = transaction.getAmount();

        BigDecimal expectedAmount = BigDecimal.valueOf(-40.35);
        Assertions.assertEquals(expectedAmount, resultedAmount);
    }

    @Test
    void getAmount_operationTypeHasMultiplierEqualsOne_returnPositiveAmount() {
        BigDecimal amount = BigDecimal.valueOf(40.35);
        OperationType operationType = OperationType.builder().id(4).multiplier(1).description("PAGAMENTO").build();
        Transaction transaction = Transaction.builder().amount(amount).account(Account.builder().build())
                .operationType(operationType).build();

        BigDecimal resultedAmount = transaction.getAmount();

        Assertions.assertEquals(amount, resultedAmount);
    }

}
