package efs.task.todoapp.init;

import efs.task.todoapp.init.annotationExecutors.BeanReaderExecutor;
import efs.task.todoapp.init.annotationExecutors.Executor;
import efs.task.todoapp.init.annotationExecutors.RestControllerExecutor;

import java.util.List;

import static efs.task.todoapp.init.DependencyContext.BEAN_MAP;
import static efs.task.todoapp.init.DependencyContext.MAPPING_MAP;

public class HandicappedDependencyInjectionReader {

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
