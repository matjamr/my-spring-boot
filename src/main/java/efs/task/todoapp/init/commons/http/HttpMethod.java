package efs.task.todoapp.init.commons.http;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HttpMethod {
    POST("POST"), GET("GET"), PUT("PUT"), DELETE("DELETE");

    private final String method;
}
