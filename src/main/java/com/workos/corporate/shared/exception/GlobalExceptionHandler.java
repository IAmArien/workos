package com.workos.corporate.shared.exception;

import com.workos.corporate.presentation.constants.HttpResponseStatus;
import com.workos.corporate.shared.response.ApiErrors;
import com.workos.corporate.shared.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException ex) {
        ApiResponse<Object> response;
        if (ex.getErrors().isEmpty()) {
            response = ApiResponse.error(
                HttpResponseStatus.fromCode(ex.getStatusCode()),
                new ApiErrors(ex.getMessage(), ex.getErrorCode())
            );
        } else {
            response = ApiResponse.errors(
                HttpResponseStatus.fromCode(ex.getStatusCode()),
                ex.getErrors()
            );
        }
        return response.asEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        ApiErrors errors = new ApiErrors(ex.getMessage(), null);
        ApiResponse<Object> response = ApiResponse.error(
            HttpResponseStatus.INTERNAL_SERVER_ERROR,
            errors
        );
        return response.asEntity();
    }
}
