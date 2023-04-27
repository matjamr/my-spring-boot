package efs.task.todoapp.init.annotationExecutors;

import efs.task.todoapp.annotations.Component;
import efs.task.todoapp.annotations.Repository;
import efs.task.todoapp.annotations.RestController;
import efs.task.todoapp.annotations.Service;
import efs.task.todoapp.init.Bean;
import efs.task.todoapp.init.ClassReader;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static efs.task.todoapp.init.DependencyContext.BEAN_MAP;

public class BeanReaderExecutor implements Executor {

    private static final List<Class<? extends Annotation>> supportedAnnotations = List.of(
            Service.class,
            RestController.class,
            Repository.class
    );

    private static final String BASE_PACKAGE = "efs.task.todoapp";

    @Override
    public void execute() {
        ClassReader.findAnnotatedClasses(BASE_PACKAGE, Component.class).stream()
                .forEach(class_ -> {
                    assertValidAnnotations(class_);

                    try {
                        BEAN_MAP.put(class_.getName(),
                                Bean.builder()
                                        .instance(class_.getDeclaredConstructor().newInstance())
                                        .class_(class_)
                                        .build()
                        );
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static void assertValidAnnotations(Class<?> class_) {
        Arrays.stream(class_.getAnnotations())
                .forEach(annotation -> {
                    assert supportedAnnotations.contains(annotation.getClass());
                });
    }
}
