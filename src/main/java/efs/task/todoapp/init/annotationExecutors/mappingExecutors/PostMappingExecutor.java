package efs.task.todoapp.init.annotationExecutors.mappingExecutors;

import efs.task.todoapp.init.annotationExecutors.annotations.PostMapping;
import efs.task.todoapp.init.commons.http.HttpMethod;
import efs.task.todoapp.init.DependencyContext;
import efs.task.todoapp.init.MappingRecord;

import java.lang.reflect.Method;

public class PostMappingExecutor implements MappingExecutor {

    @Override
    public void execute(Method method) {
        DependencyContext.MAPPING_MAP.put(new MappingRecord(
                method.getAnnotation(PostMapping.class).path(),
                HttpMethod.POST,
                false), method);
    }
}
