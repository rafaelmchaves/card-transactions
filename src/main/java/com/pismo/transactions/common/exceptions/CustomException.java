package com.pismo.transactions.common.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final String message;
    private final String code;

    public CustomException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, String code, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.code = code;
    }
}
