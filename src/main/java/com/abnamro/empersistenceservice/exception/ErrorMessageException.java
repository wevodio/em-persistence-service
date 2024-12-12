package com.abnamro.empersistenceservice.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ErrorMessageException extends RuntimeException {

    private final List<String> errorMessages;

    public ErrorMessageException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ErrorMessageException(String errorMessage) {
        this.errorMessages = Collections.singletonList(errorMessage);
    }

    public ErrorMessageException(String errorMessage, Throwable cause) {
        super(cause);
        this.errorMessages = Collections.singletonList(errorMessage);
    }
}
