package efs.task.todoapp.model.pojos;

import efs.task.todoapp.init.commons.error.ServiceError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import lombok.*;

import static java.util.Objects.nonNull;

// metoda wytworcza, proxy
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDtoProxy {

    public static UserDto createUser(UserDto userDto) {
        if(!isValid(userDto)) {
            throw new ServiceError("Invalid user data", HttpStatus.BAD_REQUEST);
        }

        return userDto;
    }

    private static boolean isValid(UserDto userDto) {
        return nonNull(userDto.getUsername()) && nonNull(userDto.getPassword());
    }
}
