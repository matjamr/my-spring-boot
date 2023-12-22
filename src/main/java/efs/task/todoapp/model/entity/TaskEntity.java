package efs.task.todoapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Builder
@Data
@Entity
@Table(name = "task_entity")
public class TaskEntity {

    @Id
    private UUID id;

    @Column
    private String description;

    @Column
    private String due;

    @Column
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
