package efs.task.todoapp.init.annotationExecutors;

public interface Executor {

    default boolean shouldExecute() {
        return true;
    }

    void execute();
}
