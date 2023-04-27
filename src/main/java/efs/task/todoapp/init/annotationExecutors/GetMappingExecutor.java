package efs.task.todoapp.init.annotationExecutors;

import efs.task.todoapp.annotations.GetMapping;
import efs.task.todoapp.commons.http.HTTP_METHOD;
import efs.task.todoapp.init.DependencyContext;
import efs.task.todoapp.init.MappingRecord;

import java.lang.reflect.Method;

public class GetMappingExecutor implements MappingExecutor {

    @Override
    public void execute(Method method) {
        DependencyContext.MAPPING_MAP.put(new MappingRecord(
                method.getAnnotation(GetMapping.class).path(),
                HTTP_METHOD.GET), method);
    }

}
