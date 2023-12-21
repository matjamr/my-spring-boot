package efs.task.todoapp.model.entity;

import efs.task.todoapp.init.commons.error.ServiceError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.nonNull;

// metoda wytworcza, proxy
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskEntityProxy {

    public static TaskEntity createTask(final TaskEntity taskEntity) {
        if(!isValid(taskEntity))
            throw new ServiceError("Invalid task entity data provided", HttpStatus.BAD_REQUEST);
        return taskEntity;
    }

    private static boolean isValid(final TaskEntity taskEntity) {
        return nonNull(taskEntity.getDue()) && nonNull(taskEntity.getDescription());
    }
}
