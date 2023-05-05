package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.GetMapping;
import efs.task.todoapp.init.annotationExecutors.annotations.PostMapping;
import efs.task.todoapp.init.annotationExecutors.annotations.RestController;

import java.util.List;

@Component
@RestController
public class DataController {

    @GetMapping(path = "/todo/task")
    public String getTasks() {
        return "121231231!@#!@#!@#!";
    }

    @GetMapping(path = "/todo/task/{id}")
    public String getTaskById() {
        return "121231231!@#!@#!@#!";
    }

    @PutMapping(path = "/todo/task/{id}")
    public String updateTaskById() {
        return "121231231!@#!@#!@#!";
    }

    @DeleteMapping(path = "/todo/task/{id}")
    public String deleteTaskById() {
        return "121231231!@#!@#!@#!";
    }

    @PostMapping(path = "/todo/task")
    public List<String> test2Method() {
        return List.of("121231231!@#!@#!@#!", "Bosze to dziala XD");
    }

}
