package efs.task.todoapp.web;

import efs.task.todoapp.annotations.Component;
import efs.task.todoapp.annotations.GetMapping;
import efs.task.todoapp.annotations.RestController;
import efs.task.todoapp.repository.TaskRepository;

@Component
@RestController
public class UserController {

    public UserController(DataController dataController, TaskRepository taskRepository) {
        dataController.dziala();
    }

    @GetMapping(path = "asdawd")
    public void anana() {
        System.out.println("!@#!@#!@#!");
    }
}
