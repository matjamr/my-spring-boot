package efs.task.todoapp.model.pojos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String username;

    private String password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
