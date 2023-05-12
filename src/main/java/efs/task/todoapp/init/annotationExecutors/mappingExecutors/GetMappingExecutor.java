package efs.task.todoapp.init.annotationExecutors.mappingExecutors;

import efs.task.todoapp.init.annotationExecutors.annotations.GetMapping;
import efs.task.todoapp.init.commons.http.HttpMethod;
import efs.task.todoapp.init.DependencyContext;
import efs.task.todoapp.init.MappingRecord;

import java.lang.reflect.Method;

public class GetMappingExecutor implements MappingExecutor {

    @Override
    public void execute(Method method) {
        DependencyContext.MAPPING_MAP.put(new MappingRecord(
                method.getAnnotation(GetMapping.class).path(),
                HttpMethod.GET,
                method.getAnnotation(GetMapping.class).secured()), method);
    }

}
