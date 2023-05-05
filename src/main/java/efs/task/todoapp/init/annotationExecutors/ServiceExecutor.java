package efs.task.todoapp.init.annotationExecutors;

public class ServiceExecutor implements Executor {
    @Override
    public boolean shouldExecute() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("Reading service...");
    }

}
