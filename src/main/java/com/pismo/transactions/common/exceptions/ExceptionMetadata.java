package com.pismo.transactions.common.exceptions;

import org.springframework.http.HttpStatus;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ ElementType.TYPE })
public @interface ExceptionMetadata {

    Level logLevel() default Level.ERROR;

    HttpStatus httpStatus() default HttpStatus.INTERNAL_SERVER_ERROR;

    public static enum Level {
        OFF, ERROR, WARN, INFO, DEBUG, TRACE;

        private Level() {
        }
    }

}
