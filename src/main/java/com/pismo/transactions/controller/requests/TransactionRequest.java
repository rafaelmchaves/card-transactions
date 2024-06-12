package com.pismo.transactions.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class TransactionRequest {

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("operation_type_id")
    private String operationTypeId;

    private BigDecimal amount;

}
