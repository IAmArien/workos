package com.workos.corporate.shared.response;

import com.workos.corporate.presentation.constants.HttpResponseStatus;
import jakarta.annotation.Nullable;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;

public class ApiResponse<T> {

    private final boolean success;
    private final int status;
    private final String message;
    @Nullable private final String description;
    @Nullable private final T data;
    @Nullable private final List<ApiErrors> errors;
    private final Instant timestamp;

    public ApiResponse(
        boolean success,
        int status,
        String message,
        @Nullable String description,
        @Nullable T data,
        @Nullable List<ApiErrors> errors
    ) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.description = description;
        this.data = data;
        this.errors = errors;
        this.timestamp = Instant.now();
    }

    public static <T> ApiResponse<T> success(T data, HttpResponseStatus status, String description) {
        return new ApiResponse<>(true, status.getCode(), status.getMessage(), description, data, null);
    }

    public static <T> ApiResponse<T> error(HttpResponseStatus status, ApiErrors error) {
        return new ApiResponse<>(false, status.getCode(), status.getMessage(), null, null, List.of(error));
    }

    public static <T> ApiResponse<T> errors(HttpResponseStatus status, List<ApiErrors> errors) {
        return new ApiResponse<>(false, status.getCode(), status.getMessage(), null, null, errors);
    }

    public ResponseEntity<ApiResponse<T>> asEntity() {
        return ResponseEntity.status(this.status).body(this);
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Object getErrors() {
        return errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
