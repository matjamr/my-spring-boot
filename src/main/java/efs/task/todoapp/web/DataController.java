package efs.task.todoapp.web;

import efs.task.todoapp.annotations.Component;
import efs.task.todoapp.annotations.GetMapping;
import efs.task.todoapp.annotations.PostMapping;
import efs.task.todoapp.annotations.RestController;

@Component
@RestController
public class DataController {

    @GetMapping(path = "/test")
    public void ananabb() {
        System.out.println(" 121231231!@#!@#!@#!");
    }


    @PostMapping(path = "/it-works!")
    public void dziala() {
        System.out.println("it works LOL");
    }
}
