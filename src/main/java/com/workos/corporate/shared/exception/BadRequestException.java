package com.workos.corporate.shared.exception;

import com.workos.corporate.presentation.constants.HttpResponseStatus;
import jakarta.annotation.Nullable;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message, HttpResponseStatus.BAD_REQUEST.getCode());
    }

    public BadRequestException(String message, @Nullable String errorCode) {
        super(message, HttpResponseStatus.BAD_REQUEST.getCode(), errorCode);
    }
}
