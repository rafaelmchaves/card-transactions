package com.pismo.transactions.adapter.controller.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class TransactionRequest {

    @NotBlank(message = "Account id cannot be null")
    @JsonProperty("account_id")
    private String accountId;

    @NotNull(message = "Operation type cannot be null")
    @JsonProperty("operation_type_id")
    private Integer operationTypeId;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must to be greater or equal than 0")
    private BigDecimal amount;

}
