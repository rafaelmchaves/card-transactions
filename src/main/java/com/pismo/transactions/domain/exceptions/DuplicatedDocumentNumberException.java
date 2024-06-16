package com.pismo.transactions.domain.exceptions;

import com.pismo.transactions.common.exceptions.CustomException;
import com.pismo.transactions.common.exceptions.ExceptionMetadata;
import org.springframework.http.HttpStatus;

@ExceptionMetadata(httpStatus = HttpStatus.BAD_REQUEST)
public class DuplicatedDocumentNumberException extends CustomException {

    public DuplicatedDocumentNumberException() {
        super(ErrorCode.DUPLICATED_DOCUMENT_NUMBER.getMessage(), ErrorCode.DUPLICATED_DOCUMENT_NUMBER.getCode());
    }

}
