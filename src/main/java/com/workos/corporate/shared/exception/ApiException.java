package com.workos.corporate.shared.exception;

import com.workos.corporate.shared.response.ApiErrors;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ApiException extends RuntimeException {

    private final int statusCode;
    @Nullable private final String errorCode;
    private final List<ApiErrors> errors;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.errors = new ArrayList<>();
        this.errorCode = null;
    }

    public ApiException(String message, int statusCode, @Nullable String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errors = new ArrayList<>();
        this.errorCode = errorCode;
    }

    public void addError(ApiErrors error) {
        this.errors.add(error);
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Nullable
    public String getErrorCode() {
        return errorCode;
    }

    public List<ApiErrors> getErrors() {
        return errors;
    }
}
