package efs.task.todoapp.service;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.Service;
import efs.task.todoapp.repository.UserEntity;
import efs.task.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity saveUser(UserEntity user) {

        userRepository.save(user);

        return user;
    }

}
