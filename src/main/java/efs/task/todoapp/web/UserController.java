package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.PostMapping;
import efs.task.todoapp.init.annotationExecutors.annotations.RequiredBody;
import efs.task.todoapp.init.annotationExecutors.annotations.RestController;
import efs.task.todoapp.repository.UserEntity;
import efs.task.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping(path = "/user")
    public UserEntity saveUser(@RequiredBody UserEntity user) {
        return userService.saveUser(user);
    }
}
