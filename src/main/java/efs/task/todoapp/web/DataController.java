package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.GetMapping;
import efs.task.todoapp.init.annotationExecutors.annotations.PostMapping;
import efs.task.todoapp.init.annotationExecutors.annotations.RestController;

import java.util.List;

@Component
@RestController
public class DataController {

    @GetMapping(path = "/test")
    public String ananabb() {
        return "121231231!@#!@#!@#!";
    }

    @GetMapping(path = "/test2")
    public List<String> test2Method() {
        return List.of("121231231!@#!@#!@#!", "Bosze to dziala XD");
    }

    @PostMapping(path = "/it-works!")
    public void dziala() {
        System.out.println("it works LOL");
    }
}
