package efs.task.todoapp.init.annotationExecutors;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.Repository;
import efs.task.todoapp.init.annotationExecutors.annotations.RestController;
import efs.task.todoapp.init.annotationExecutors.annotations.Service;
import efs.task.todoapp.init.Bean;
import efs.task.todoapp.init.ClassReader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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


    // TODO refactor
    @Override
    public void execute() {
        List<Class<?>> classesToProcess = ClassReader.findAnnotatedClasses(BASE_PACKAGE, Component.class);
        List<Class<?>> notFull = new ArrayList<>(classesToProcess   );
        List<Class<?>> classesToRemove = new ArrayList<>();

        while (!notFull.isEmpty()) {

            classesToProcess.removeAll(classesToRemove);

            notFull.clear();
            classesToRemove.clear();

            for (var class_ : classesToProcess) {
                assertValidAnnotations(class_);

                if (class_.getDeclaredConstructors().length > 1) {
                    throw new RuntimeException("More then one constructor, which is unambiguous");
                }

                Constructor<?> constructor = class_.getDeclaredConstructors()[0];

                if (constructor.getParameterTypes().length == 0) {
                    try {
                        BEAN_MAP.put(class_,
                                Bean.builder()
                                        .instance(class_.getDeclaredConstructor().newInstance())
                                        .class_(class_)
                                        .build()
                        );

                        classesToRemove.add(class_);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        // TODO adjust custom exception to be fail fast
                        throw new RuntimeException(e);
                    }
                } else {

                    if (!Arrays.stream(constructor.getParameterTypes())
                            .allMatch(BEAN_MAP::containsKey)) {
                        notFull.add(class_);
                    } else {

                        List<Bean> beanList = Arrays.stream(constructor.getParameterTypes())
                                .map(BEAN_MAP::get)
                                .toList();

                        try {
                            BEAN_MAP.put(class_,
                                    Bean.builder()
                                            .instance(constructor.newInstance(beanList.stream()
                                                    .map(Bean::getInstance)
                                                    .toArray(Object[]::new)))
                                            .class_(class_)
                                            .build()
                            );

                            classesToRemove.add(class_);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            // TODO adjust custom exception to be fail fast
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        System.out.println(BEAN_MAP);
    }

    private static void assertValidAnnotations(Class<?> class_) {
        Arrays.stream(class_.getAnnotations())
                .forEach(annotation -> {
                    assert supportedAnnotations.contains(annotation.getClass());
                });
    }
}
