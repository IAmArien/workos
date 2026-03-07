package com.workos.corporate.shared.exception;

import com.workos.corporate.presentation.constants.HttpResponseStatus;
import jakarta.annotation.Nullable;

public class UnprocessableEntityException extends ApiException {

    public UnprocessableEntityException(String message) {
        super(message, HttpResponseStatus.UNPROCESSABLE_ENTITY.getCode());
    }

    public UnprocessableEntityException(String message, @Nullable String errorCode) {
        super(message, HttpResponseStatus.BAD_REQUEST.getCode(), errorCode);
    }
}
