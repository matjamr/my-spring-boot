package efs.task.todoapp.init;

import efs.task.todoapp.annotations.RestController;
import efs.task.todoapp.annotations.Service;
import efs.task.todoapp.init.annotationExecutors.BeanReaderExecutor;
import efs.task.todoapp.init.annotationExecutors.Executor;
import efs.task.todoapp.init.annotationExecutors.RestControllerExecutor;
import efs.task.todoapp.init.annotationExecutors.ServiceExecutor;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import static efs.task.todoapp.init.DependencyContext.BEAN_MAP;
import static efs.task.todoapp.init.DependencyContext.MAPPING_MAP;

public class HandicappedDependencyInjectionReader {



    private static final Map<Class<? extends Annotation>, Executor> processingMap = Map.of(
            RestController.class, new RestControllerExecutor(),
            Service.class, new ServiceExecutor()
    );



    public static void run()  {
        List.of(
                new BeanReaderExecutor(),
                new RestControllerExecutor()
                        )
                        .forEach(Executor::execute);

        System.out.println(BEAN_MAP);
        System.out.println(MAPPING_MAP);

    }

}
