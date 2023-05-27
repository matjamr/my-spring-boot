package efs.task.todoapp.repository;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.model.entity.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.Objects.isNull;

@Component
@efs.task.todoapp.init.annotationExecutors.annotations.Repository
public class UserRepository implements Repository<String, UserEntity> {

    public final Map<String, UserEntity> db = new HashMap<>();

    @Override
    public String save(UserEntity userEntity) {
        db.put(userEntity.getUsername(), userEntity);
        return userEntity.getUsername();
    }

    @Override
    public UserEntity query(String s) {
        return db.get(s);
    }

    @Override
    public List<UserEntity> query(Predicate<UserEntity> condition) {
        return db.values().stream()
                .filter(condition)
                .toList();
    }

    @Override
    public UserEntity update(String s, UserEntity userEntity) {
        return db.replace(s, userEntity);
    }

    @Override
    public boolean delete(String s) {
        return isNull(db.remove(s));
    }
}
