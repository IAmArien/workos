package com.workos.corporate.shared.exception;

import com.workos.corporate.presentation.constants.HttpResponseStatus;

public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(message, HttpResponseStatus.NOT_FOUND.getCode());
    }
}
