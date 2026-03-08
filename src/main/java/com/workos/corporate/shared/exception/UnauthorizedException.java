package com.workos.corporate.shared.exception;

import com.workos.corporate.presentation.constants.HttpResponseStatus;
import jakarta.annotation.Nullable;

public class UnauthorizedException extends ApiException {

    public UnauthorizedException(String message) {
        super(message, HttpResponseStatus.UNAUTHORIZED.getCode());
    }

    public UnauthorizedException(String message, @Nullable String errorCode) {
        super(message, HttpResponseStatus.UNAUTHORIZED.getCode(), errorCode);
    }
}
