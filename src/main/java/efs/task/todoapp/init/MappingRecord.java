package efs.task.todoapp.init;

import efs.task.todoapp.init.commons.http.HTTP_METHOD;

public record MappingRecord(String path, HTTP_METHOD method) {
}
