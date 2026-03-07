package com.workos.corporate.shared.exception;

import com.workos.corporate.presentation.constants.HttpResponseStatus;
import jakarta.annotation.Nullable;

public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(message, HttpResponseStatus.NOT_FOUND.getCode());
    }

    public NotFoundException(String message, @Nullable String errorCode) {
        super(message, HttpResponseStatus.NOT_FOUND.getCode(), errorCode);
    }
}
