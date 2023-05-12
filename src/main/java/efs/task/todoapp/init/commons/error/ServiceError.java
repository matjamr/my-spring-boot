package efs.task.todoapp.init.commons.error;

import efs.task.todoapp.init.commons.http.HttpStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ServiceError extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public String getMessage() {
        return "";
    }
}
