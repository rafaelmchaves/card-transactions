package com.pismo.transactions.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    ACCOUNT_NOT_FOUND("ACC_01", "Account not found"),
    DUPLICATED_DOCUMENT_NUMBER("ACC_02", "Document number already exists in our base"),
    OPERATION_TYPE_NOT_FOUND("OPT_01", "Operation type not found");

    private final String code;
    private final String message;
}
