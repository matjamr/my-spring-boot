package efs.task.todoapp.init.commons.http;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HttpMethod {
    POST("POST"), GET("GET");

    private final String method;
}
