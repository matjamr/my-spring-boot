package efs.task.todoapp.service;

import efs.task.todoapp.exceptions.UserNotFoundException;
import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.Service;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.repository.UserEntity;
import efs.task.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String saveUser(UserDto user) throws Exception {

        if(isNull(user.getUsername()) || isNull(user.getPassword()))
            throw new Exception("Invalid user data");

        return userRepository.save(UserEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .username(user.getUsername())
                        .password(user.getPassword())
                .build());
    }

}
