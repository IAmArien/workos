package com.workos.corporate.shared.response;

import jakarta.annotation.Nullable;

public class ApiErrors {

    private final String description;
    @Nullable private final String code;

    public ApiErrors(String description, @Nullable String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    @Nullable
    public String getCode() {
        return code;
    }
}
