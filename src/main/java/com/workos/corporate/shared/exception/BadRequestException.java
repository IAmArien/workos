package com.workos.corporate.shared.exception;

import com.workos.corporate.presentation.constants.HttpResponseStatus;

public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message, HttpResponseStatus.BAD_REQUEST.getCode());
    }
}
