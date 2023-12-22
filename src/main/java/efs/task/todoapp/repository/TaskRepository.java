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
public class TaskRepository implements Repository<UUID, TaskEntity> {

    private final Map<UUID, TaskEntity> map = new HashMap<>();
    private final SessionFactory sessionFactory;

    public TaskRepository() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
//        List<Employee> employees = session.createQuery("FROM Employee", Employee.class).getResultList();
//        for (Employee employee : employees) {
//            System.out.println("ID: " + employee.getId() + ", Name: " + employee.getName());
//        }

        List<TaskEntity> taskEntities = session.createQuery("FROM TaskEntity ", TaskEntity.class).getResultList();

        transaction.commit();
        session.close();
//        sessionFactory.close();
    }



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
