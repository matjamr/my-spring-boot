package efs.task.todoapp.service;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.Service;
import efs.task.todoapp.repository.UserEntity;

@Service
@Component
public class UserService {
    public UserEntity saveUser(UserEntity user) {

        System.out.println("MVC WORKS " + user);

        return user;
    }
}
