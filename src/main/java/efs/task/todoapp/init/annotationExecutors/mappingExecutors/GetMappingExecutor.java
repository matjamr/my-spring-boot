package efs.task.todoapp.init.annotationExecutors.mappingExecutors;

import efs.task.todoapp.init.DependencyContext;
import efs.task.todoapp.init.MappingRecord;
import efs.task.todoapp.init.annotationExecutors.annotations.GetMapping;
import efs.task.todoapp.init.annotationExecutors.annotations.PathVariable;
import efs.task.todoapp.init.commons.http.HttpMethod;
import efs.task.todoapp.init.commons.http.HttpUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class GetMappingExecutor implements MappingExecutor {

    @Override
    public void execute(Method method) {
        DependencyContext.MAPPING_MAP.put(new MappingRecord(
                HttpUtils.handlePathVar(method.getAnnotation(GetMapping.class).path()),
                HttpMethod.GET,
                containsPathVariable(method)), method);
    }

    private boolean containsPathVariable(Method method) {
        return Arrays.stream(method.getParameters())
                .anyMatch(param -> param.isAnnotationPresent(PathVariable.class));
    }

}
