package efs.task.todoapp.model.pojos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataDto {
    private String description;
    private String due;
}
