package efs.task.todoapp.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserEntity {
    private String id;
    private String username;
    private String password;
}
