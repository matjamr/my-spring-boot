package efs.task.todoapp.init.annotationExecutors.mappingExecutors;

import java.lang.reflect.Method;

public interface MappingExecutor {

    void execute(Method method);
}
