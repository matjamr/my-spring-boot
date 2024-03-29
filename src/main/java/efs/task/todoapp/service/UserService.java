package efs.task.todoapp.service;

import com.google.gson.Gson;
import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.Service;
import efs.task.todoapp.init.commons.error.ServiceError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.model.entity.UserEntity;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.model.pojos.UserDtoProxy;
import efs.task.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static efs.task.todoapp.model.pojos.UserDtoProxy.createUser;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final Gson gson = new Gson();

    public void saveUser(UserDto user) throws ServiceError {
        if(nonNull(userRepository.query(user.getUsername()))) {
            throw new ServiceError("User with this data exists", HttpStatus.ALREADY_EXISTS);
        }

        userRepository.save(UserEntity.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                .build());
    }

    public UserDto verifyUser(String encodedUser) {

        String[] data = new String(Base64.getDecoder().decode(encodedUser)).split(":");

        UserDto userDto = createUser(UserDto.builder()
                .username(data[0])
                .password(data[1])
                .build());

        System.out.println("Veryfiing user: " + createUser(userDto));
//        System.out.println(userRepository.db);

        return Optional.ofNullable(userRepository.query(userDto.getUsername()))
                .filter(user -> user.getPassword().equals(userDto.getPassword()))
                .map(userEntity -> userDto)
                .orElse(null);
    }

}
