package efs.task.todoapp.init;

import efs.task.todoapp.annotations.RestController;
import efs.task.todoapp.annotations.Service;
import efs.task.todoapp.init.annotationExecutors.BeanReaderExecutor;
import efs.task.todoapp.init.annotationExecutors.Executor;
import efs.task.todoapp.init.annotationExecutors.RestControllerExecutor;
import efs.task.todoapp.init.annotationExecutors.ServiceExecutor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static efs.task.todoapp.init.DependencyContext.BEAN_MAP;

public class HandicappedDependencyInjectionReader {



    private static final Map<Class<? extends Annotation>, Executor> processingMap = Map.of(
            RestController.class, new RestControllerExecutor(),
            Service.class, new ServiceExecutor()
    );



    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {


        List.of(
                new BeanReaderExecutor(),
                new RestControllerExecutor()
                        )
                        .forEach(Executor::execute);

        System.out.println(BEAN_MAP);

//
//        ClassReader.findAnnotatedClasses(BASE_PACKAGE, Component.class).stream()
////                .filter(CLASS -> Arrays.stream(CLASS.getAnnotations()).toList().contains(RestController.class))
//                .forEach(CLASS -> {
//            try {
//                Object obj =  CLASS.getDeclaredConstructor().newInstance();
//                obj.getClass().getMethod("anana").invoke(obj);
//
//                Arrays.stream(CLASS.getMethods())
//                        .filter(method -> Arrays.stream(method.getAnnotations()).toList().contains(GetMapping.class))
//                        .forEach(method -> {
//
//                            try {
//                                method.invoke(obj);
//                            } catch (IllegalAccessException | InvocationTargetException e) {
//                                throw new RuntimeException(e);
//                            }
//
//                        });
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });



        System.out.println("aaa");

//        new RestControllerExecutor().execute(classs);

    }

}
