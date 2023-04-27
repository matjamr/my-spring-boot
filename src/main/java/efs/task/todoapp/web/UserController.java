package efs.task.todoapp.web;

import efs.task.todoapp.annotations.Component;
import efs.task.todoapp.annotations.GetMapping;
import efs.task.todoapp.annotations.RestController;

@Component
@RestController
public class UserController {

    @GetMapping(path = "asdawd")
    public void anana() {
        System.out.println("!@#!@#!@#!");
    }
}
