package efs.task.todoapp.repository;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.model.entity.TaskEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.*;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;

@Component
@efs.task.todoapp.init.annotationExecutors.annotations.Repository
public class TaskRepository implements Repository<String, TaskEntity> {
    private final DbFactory dbFactory;

    public TaskRepository(DbFactory dbFactory) {
        this.dbFactory = dbFactory;
    }

    @Override
    public String save(TaskEntity taskEntity) {
        return (String) dbFactory.save(taskEntity);
    }

    @Override
    public TaskEntity query(String id) {
        return dbFactory.findById(id, TaskEntity.class);
    }

    @Override
    public List<TaskEntity> query(Predicate<TaskEntity> condition) {
        return dbFactory.createQuery("SELECT a FROM task_entity a", TaskEntity.class)
                .stream()
                .filter(condition)
                .toList();
    }

    @Override
    public TaskEntity update(String uuid, TaskEntity taskEntity) {
        return dbFactory.update(uuid, taskEntity, TaskEntity.class);
    }

    @Override
    public boolean delete(String id) {
        dbFactory.delete(id, TaskEntity.class);
        return true;
    }
}
