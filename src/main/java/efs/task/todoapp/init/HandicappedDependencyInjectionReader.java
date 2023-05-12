package efs.task.todoapp.init;

import efs.task.todoapp.init.annotationExecutors.BeanReaderExecutor;
import efs.task.todoapp.init.annotationExecutors.Executor;
import efs.task.todoapp.init.annotationExecutors.RestControllerExecutor;

import java.util.List;

public class HandicappedDependencyInjectionReader {

    public static void run()  {
        List.of(
                new BeanReaderExecutor(),
                new RestControllerExecutor()
                        )
                        .forEach(Executor::execute);
    }

}
