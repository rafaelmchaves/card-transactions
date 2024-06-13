package com.pismo.transactions.domain.exceptions;

import com.pismo.transactions.common.exceptions.CustomException;
import com.pismo.transactions.common.exceptions.ExceptionMetadata;
import org.springframework.http.HttpStatus;

@ExceptionMetadata(httpStatus = HttpStatus.BAD_REQUEST)
public class OperationTypeNotFoundException extends CustomException {

    public OperationTypeNotFoundException() {
        super(ErrorCode.OPERATION_TYPE_NOT_FOUND.getMessage(), ErrorCode.OPERATION_TYPE_NOT_FOUND.getCode());
    }
}
