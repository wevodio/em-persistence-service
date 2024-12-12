package com.abnamro.empersistenceservice.exception;

import com.abnamro.empersistenceservice.generated.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(ErrorMessageException.class)
    public ResponseEntity<ErrorResponse> handle(final ErrorMessageException e) {
        var errorResponse = new ErrorResponse();
        errorResponse.setErrorMessages(e.getErrorMessages());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
