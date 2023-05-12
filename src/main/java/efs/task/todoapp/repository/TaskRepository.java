package efs.task.todoapp.repository;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.model.entity.TaskEntity;

import java.util.*;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;

@Component
@efs.task.todoapp.init.annotationExecutors.annotations.Repository
public class TaskRepository implements Repository<UUID, TaskEntity> {

    private final Map<UUID, TaskEntity> map = new HashMap<>();

    @Override
    public UUID save(TaskEntity taskEntity) {
        return Optional.ofNullable(map.put(taskEntity.getId(), taskEntity))
                .map(TaskEntity::getId)
                .orElse(taskEntity.getId());
    }

    @Override
    public TaskEntity query(UUID uuid) {
        return map.get(uuid);
    }

    @Override
    public List<TaskEntity> query(Predicate<TaskEntity> condition) {
        return map.values().stream()
                .filter(condition)
                .toList();
    }

    @Override
    public TaskEntity update(UUID uuid, TaskEntity taskEntity) {
        return map.replace(uuid, taskEntity);
    }

    @Override
    public boolean delete(UUID uuid) {
        return nonNull(map.remove(uuid));
    }
}
