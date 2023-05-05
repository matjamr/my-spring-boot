package efs.task.todoapp.init.annotationExecutors;

import efs.task.todoapp.init.annotationExecutors.annotations.GetMapping;
import efs.task.todoapp.init.annotationExecutors.annotations.PostMapping;
import efs.task.todoapp.init.annotationExecutors.annotations.RestController;
import efs.task.todoapp.init.annotationExecutors.mappingExecutors.GetMappingExecutor;
import efs.task.todoapp.init.annotationExecutors.mappingExecutors.MappingExecutor;
import efs.task.todoapp.init.annotationExecutors.mappingExecutors.PostMappingExecutor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static efs.task.todoapp.init.DependencyContext.BEAN_MAP;

public class RestControllerExecutor implements Executor {

    private static final List<Class<? extends Annotation>> supportedAnnotations = List.of(
            GetMapping.class,
            PostMapping.class
    );

    private static final Map<Class<? extends Annotation>, MappingExecutor> processingMap = Map.of(
            GetMapping.class, new GetMappingExecutor(),
            PostMapping.class, new PostMappingExecutor()
    );

    @Override
    public void execute() {

        BEAN_MAP.values().stream()
                .filter(bean -> bean.getClass_().isAnnotationPresent(RestController.class))
                .forEach(bean -> Arrays.stream(bean.getClass_().getMethods())
                        .filter(this::isOk)
                        .forEach(method -> {
                            MappingExecutor mappingExecutor = processingMap.get(Arrays.stream(method.getDeclaredAnnotations())
                                            .map(Annotation::annotationType)
                                            .findFirst().get());

                            mappingExecutor.execute(method);
                        })
                );
    }

    private boolean isOk(Method method) {
        return supportedAnnotations.stream()
                .anyMatch(method::isAnnotationPresent);
    }
}
