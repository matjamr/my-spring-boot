package efs.task.todoapp.init.commons.http;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HttpStatus {
    OK(200),
    CREATED(201),
    INTERNAL_ERROR(500),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    BAD_REQUEST(400);

    private final int statusCode;

    public int getStatusCode() {
        return statusCode;
    }
}
