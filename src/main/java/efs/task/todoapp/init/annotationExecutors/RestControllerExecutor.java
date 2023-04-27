package efs.task.todoapp.init.annotationExecutors;

import efs.task.todoapp.annotations.GetMapping;
import efs.task.todoapp.annotations.PostMapping;
import efs.task.todoapp.annotations.RestController;

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

    private static final Map<Class<? extends Annotation>, Executor> processingMap = Map.of(
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
                                processingMap.get(Arrays.stream(method.getAnnotations())
                                        .findFirst().get().getClass())
                                        .execute();
                            })
                );
    }

    private boolean isOk(Method method) {
        return supportedAnnotations.stream()
                .anyMatch(method::isAnnotationPresent);
    }
}
