package com.pismo.transactions.domain.exceptions;

import com.pismo.transactions.common.exceptions.CustomException;
import com.pismo.transactions.common.exceptions.ExceptionMetadata;
import org.springframework.http.HttpStatus;

@ExceptionMetadata(httpStatus = HttpStatus.BAD_REQUEST)
public class AccountNotFoundException extends CustomException {

    public AccountNotFoundException() {
        super(ErrorCode.ACCOUNT_NOT_FOUND.getMessage(), ErrorCode.ACCOUNT_NOT_FOUND.getCode());
    }
}
