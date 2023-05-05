package efs.task.todoapp.init.commons.http;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HTTP_METHOD {
    POST("POST"), GET("GET");

    private final String method;
}
