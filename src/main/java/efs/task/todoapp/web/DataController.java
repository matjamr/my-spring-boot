package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.*;

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
        return "121231231!@#!@#!@#! GETTT";
    }

    @PutMapping(path = "/todo/task/{id}")
    public String updateTaskById() {
        return "121231231!@#!@#!@#! UDPATE";
    }

    @DeleteMapping(path = "/todo/task/{id}")
    public String deleteTaskById() {
        return "121231231!@#!@#!@#! DELETE";
    }

    @PostMapping(path = "/todo/task")
    public List<String> test2Method() {
        return List.of("121231231!@#!@#!@#!", "Bosze to dziala XD");
    }

}
