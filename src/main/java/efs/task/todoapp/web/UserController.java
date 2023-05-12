package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.*;
import efs.task.todoapp.init.commons.error.HttpStatusError;
import efs.task.todoapp.init.commons.error.ServiceError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/user")
    @Response(status = HttpStatus.CREATED)
    public void saveUser(@RequiredBody UserDto user) {
        try {
            userService.saveUser(user);
        } catch (ServiceError e) {
            throw new HttpStatusError(e.getMessage(), e.getHttpStatus());
        }
    }
}
