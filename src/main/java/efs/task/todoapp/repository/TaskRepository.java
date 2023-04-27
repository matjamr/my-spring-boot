package efs.task.todoapp.repository;

import efs.task.todoapp.annotations.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

@Component
@efs.task.todoapp.annotations.Repository
public class TaskRepository implements Repository<UUID, TaskEntity> {

    private final Map<UUID, TaskEntity> map = new HashMap<>();

    @Override
    public UUID save(TaskEntity taskEntity) {
        return null;
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
