package com.abnamro.empersistenceservice.presenter;

import com.abnamro.empersistenceservice.generated.model.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class JsonGenericSuccessPresenter implements GenericSuccessPresenter {

    private ResponseEntity<SuccessResponse> responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .build();

    @Override
    public void success(String message) {
        responseEntity = ResponseEntity.ok(constructSuccessResponse(message));
    }

    public ResponseEntity<SuccessResponse> toResponseEntity() {
        return responseEntity;
    }

    private SuccessResponse constructSuccessResponse(String message) {
        var successResponse = new SuccessResponse();
        successResponse.setMessage(Optional.of(message));
        return successResponse;
    }
}
