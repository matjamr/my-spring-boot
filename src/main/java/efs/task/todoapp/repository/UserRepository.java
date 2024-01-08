package efs.task.todoapp.repository;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.model.entity.TaskEntity;
import efs.task.todoapp.model.entity.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.Objects.isNull;

@Component
@efs.task.todoapp.init.annotationExecutors.annotations.Repository
public class UserRepository implements Repository<String, UserEntity> {
    private final DbFactory dbFactory;

    public UserRepository(DbFactory dbFactory) {
        this.dbFactory = dbFactory;
    }

    @Override
    public String save(UserEntity userEntity) {
        var ret = dbFactory.save(userEntity);

        return userEntity.getUsername();
    }

    @Override
    public UserEntity query(String id) {
        return dbFactory.findById(id, UserEntity.class);
    }

    @Override
    public List<UserEntity> query(Predicate<UserEntity> condition) {
        var res = dbFactory.createQuery("SELECT a FROM user_entity a", UserEntity.class);

        return res.stream()
                .filter(condition)
                .toList();
    }

    @Override
    public UserEntity update(String id, UserEntity userEntity) {
        return dbFactory.update(id, (entity) -> {
            entity.setUsername(userEntity.getUsername());
            entity.setPassword(userEntity.getPassword());
        }, UserEntity.class);
    }

    @Override
    public boolean delete(String id) {
        return dbFactory.delete(id, UserEntity.class);
    }
}
