package efs.task.todoapp.repository;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.model.entity.TaskEntity;

import java.util.*;
import java.util.function.Predicate;

@Component
@efs.task.todoapp.init.annotationExecutors.annotations.Repository
public class TaskRepository implements Repository<UUID, TaskEntity> {

    private final Map<UUID, TaskEntity> map = new HashMap<>();

    @Override
    public UUID save(TaskEntity taskEntity) {
        return Optional.ofNullable(map.put(taskEntity.getId(), taskEntity))
                .map(TaskEntity::getId)
                .orElse(null);
    }

    @Override
    public TaskEntity query(UUID uuid) {
        return null;
    }

    @Override
    public List<TaskEntity> query(Predicate<TaskEntity> condition) {
        return null;
    }

    @Override
    public TaskEntity update(UUID uuid, TaskEntity taskEntity) {
        return null;
    }

    @Override
    public boolean delete(UUID uuid) {
        return false;
    }
}
