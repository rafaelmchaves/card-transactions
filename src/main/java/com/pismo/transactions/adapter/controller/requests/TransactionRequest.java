package com.pismo.transactions.adapter.controller.requests;

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
    private Integer operationTypeId;

    private BigDecimal amount;

}
