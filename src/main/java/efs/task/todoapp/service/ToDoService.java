package efs.task.todoapp.service;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.Service;
import efs.task.todoapp.repository.TaskRepository;
import efs.task.todoapp.repository.UserRepository;

@Service
@Component
public class ToDoService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ToDoService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public void test() {

    }
}
