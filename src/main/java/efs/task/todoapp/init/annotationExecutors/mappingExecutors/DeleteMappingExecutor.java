package efs.task.todoapp.init.annotationExecutors.mappingExecutors;

import efs.task.todoapp.init.DependencyContext;
import efs.task.todoapp.init.MappingRecord;
import efs.task.todoapp.init.annotationExecutors.annotations.DeleteMapping;
import efs.task.todoapp.init.annotationExecutors.annotations.PathVariable;
import efs.task.todoapp.init.annotationExecutors.annotations.PutMapping;
import efs.task.todoapp.init.commons.http.HttpMethod;
import efs.task.todoapp.init.commons.http.HttpUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class DeleteMappingExecutor implements MappingExecutor {

    @Override
    public void execute(Method method) {
        DependencyContext.MAPPING_MAP.put(new MappingRecord(
                HttpUtils.handlePathVar(method.getAnnotation(DeleteMapping.class).path()),
                HttpMethod.DELETE,
                containsPathVariable(method)), method);
    }

    private boolean containsPathVariable(Method method) {
        return Arrays.stream(method.getParameters())
                .anyMatch(param -> param.isAnnotationPresent(PathVariable.class));
    }
}
