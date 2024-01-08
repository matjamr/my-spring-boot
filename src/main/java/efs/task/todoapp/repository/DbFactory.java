package efs.task.todoapp.repository;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.model.entity.TaskEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.function.Consumer;

@Component
public class DbFactory {
    private final SessionFactory sessionFactory;
    private final Session session;

    public DbFactory() {
        this.sessionFactory =  new Configuration().configure("hibernate.cfg.xml")
                .buildSessionFactory();

        session = sessionFactory.openSession();
//        session.close();
//        sessionFactory.close();
    }

    public <T> List<T> createQuery(String query, Class<T> clazz) {
        Transaction transaction = session.beginTransaction();

        List<T> resultList = session.createQuery(query, clazz).getResultList();

        transaction.commit();

        return resultList;
    }

    public <T> T findById(String id, Class<T> clazz) {
        Transaction transaction = session.beginTransaction();

        T returnedObject = session.get(clazz, id);

        transaction.commit();

        return returnedObject;
    }

    public <T> Object save(T entity) {
        Transaction transaction = session.beginTransaction();

        Object returnedObject = session.save(entity);

        transaction.commit();

        return returnedObject;
    }

    public <T> T update(String id, Consumer<T> entityConsumer, Class<T> clazz) {
        Transaction transaction = session.beginTransaction();

        T returnedObject = session.get(clazz, id);

        entityConsumer.accept(returnedObject);

        session.save(returnedObject);

        transaction.commit();

        return returnedObject;
    }

    public <T> boolean delete(String id, Class<T> clazz) {
        Transaction transaction = session.beginTransaction();

        T entityToBeDeleted = session.get(clazz, id);
        session.delete(entityToBeDeleted);

        transaction.commit();

        return true;
    }
}
