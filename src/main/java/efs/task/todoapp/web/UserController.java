package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.*;
import efs.task.todoapp.init.commons.error.HttpStatusError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.repository.UserEntity;
import efs.task.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/user")
    @Response(status = HttpStatus.CREATED)
    public String saveUser(@RequiredBody UserDto user) {
        try {
            return userService.saveUser(user);
        } catch (Exception e) {
            throw new HttpStatusError(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
