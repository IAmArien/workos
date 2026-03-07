package com.workos.corporate.shared.exception;

import com.workos.corporate.presentation.constants.HttpResponseStatus;

public class UnprocessableEntityException extends ApiException {

    public UnprocessableEntityException(String message) {
        super(message, HttpResponseStatus.UNPROCESSABLE_ENTITY.getCode());
    }
}
