package efs.task.todoapp.init.commons.http;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HttpStatus {
    OK(200),
    CREATED(201),
    INTERNAL_ERROR(500),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    ALREADY_EXISTS(409),
    BAD_REQUEST(400),
    NOT_FOUND(404);

    private final int statusCode;

    public int getStatusCode() {
        return statusCode;
    }
}
