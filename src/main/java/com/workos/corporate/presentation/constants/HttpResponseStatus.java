package com.workos.corporate.presentation.constants;

public enum HttpResponseStatus {
    SUCCESS(200, "Operation successful"),
    CREATED(201, "Resource created"),
    UNAUTHORIZED(401, "Unauthorized access"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Resource not found"),
    TOO_MANY_REQUESTS(429, "Too many requests"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable entity"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    BAD_GATEWAY(502, "Bad gateway"),
    SERVICE_UNAVAILABLE(503, "Service unavailable");

    private final int code;
    private final String message;

    HttpResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static HttpResponseStatus fromCode(int code) {
        for (HttpResponseStatus status : HttpResponseStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("No HttpResponseStatus for code: " + code);
    }
}
