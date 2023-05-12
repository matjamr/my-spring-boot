package efs.task.todoapp.service;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.Service;
import efs.task.todoapp.init.commons.error.ServiceError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.model.entity.UserEntity;
import efs.task.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(UserDto user) throws ServiceError {

        if(isNull(user.getUsername()) || isNull(user.getPassword())) {
            throw new ServiceError("Invalid user data", HttpStatus.BAD_REQUEST);
        }

        if(!userRepository.query(user1 -> user1.getUsername().equals(user.getUsername())).isEmpty()) {
            throw new ServiceError("User with this data exists", HttpStatus.ALREADY_EXISTS);
        }

        userRepository.save(UserEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .username(user.getUsername())
                        .password(user.getPassword())
                .build());
    }

}
