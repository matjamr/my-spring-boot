package efs.task.todoapp.init;

import efs.task.todoapp.init.commons.http.HttpMethod;

public record MappingRecord(String path, HttpMethod method, boolean isSecured) {
}
