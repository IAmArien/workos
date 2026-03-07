package com.workos.corporate.shared.exception;

import com.workos.corporate.shared.response.ApiErrors;

import java.util.ArrayList;
import java.util.List;

public class ApiException extends RuntimeException {

    private final int statusCode;
    private final List<ApiErrors> errors;

    public ApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.errors = new ArrayList<>();
    }

    public void addError(ApiErrors error) {
        this.errors.add(error);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<ApiErrors> getErrors() {
        return errors;
    }
}
