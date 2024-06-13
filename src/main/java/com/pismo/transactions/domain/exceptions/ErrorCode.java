package com.pismo.transactions.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    ACCOUNT_NOT_FOUND("ACC_01", "Account not found"),
    OPERATION_TYPE_NOT_FOUND("OPT_01", "Operation type not found");

    private final String code;
    private final String message;
}
