package efs.task.todoapp.model.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Builder
@Data
public class TaskEntity {
    private UUID id;
    private String description;
    private String due;
    private String createdBy;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(description, that.description) && Objects.equals(due, that.due);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, due);
    }
}
