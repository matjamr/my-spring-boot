package efs.task.todoapp.model.pojos;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserDto {

    @NonNull
    private String username;

    @NonNull
    private String password;

    public UserDto(@NonNull String username, @NonNull String password) {
        this.username = username;
        this.password = password;
    }
}
