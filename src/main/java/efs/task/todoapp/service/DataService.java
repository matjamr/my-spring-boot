package efs.task.todoapp.service;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.Service;
import efs.task.todoapp.init.commons.error.ServiceError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.mappers.Mapper;
import efs.task.todoapp.model.entity.TaskEntity;
import efs.task.todoapp.model.entity.TaskEntityProxy;
import efs.task.todoapp.model.pojos.DataDto;
import efs.task.todoapp.model.pojos.DataResponseDto;
import efs.task.todoapp.model.pojos.UUIDResponse;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.repository.TaskRepository;
import efs.task.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static efs.task.todoapp.model.entity.TaskEntityProxy.createTask;
import static java.util.Objects.isNull;

@Service
@Component
@RequiredArgsConstructor
public class DataService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final Mapper mapper;

    public UUIDResponse save(DataDto dataDto, UserDto userDto) {
        var created = taskRepository.save(createTask(TaskEntity.builder()
                .description(dataDto.getDescription())
                .due(dataDto.getDue())
                .createdBy(userDto.getUsername())
                .build()));

        return UUIDResponse.builder()
                .id(created.toString())
                .build();
    }

    public List<DataResponseDto> getTasks(UserDto createdBy) {
        return taskRepository
                .query(taskEntity -> taskEntity.getCreatedBy().equals(createdBy.getUsername()))
                .stream()
                .map(mapper::mapTo)
                .toList();
    }

    public DataResponseDto getTaskById(UserDto userDto, String id) {
        var task = taskRepository.query(id);

        if(isNull(task))
            throw new ServiceError("Task not found", HttpStatus.NOT_FOUND);

        if(!Objects.equals(task.getCreatedBy(), userDto.getUsername()))
            throw new ServiceError("Not your task", HttpStatus.FORBIDDEN);

        return mapper.mapTo(task);
    }

    public DataResponseDto putTaskById(DataDto dataDto, UserDto userDto, String id) {
        var task = taskRepository.query(id);

        if(isNull(dataDto.getDescription()) || isNull(dataDto.getDue())) {
            throw new ServiceError("Invalid data data", HttpStatus.BAD_REQUEST);
        }

        if(isNull(task))
            throw new ServiceError("Task not found", HttpStatus.NOT_FOUND);

        if(!Objects.equals(task.getCreatedBy(), userDto.getUsername()))
            throw new ServiceError("Not your task", HttpStatus.FORBIDDEN);

        return mapper.mapTo(taskRepository.update(id, task));
    }

    public void deleteTaskById(UserDto userDto, String id) {
        var task = taskRepository.query(id);

        if(isNull(task))
            throw new ServiceError("Task not found", HttpStatus.NOT_FOUND);

        if(!Objects.equals(task.getCreatedBy(), userDto.getUsername()))
            throw new ServiceError("Not your task", HttpStatus.FORBIDDEN);

        taskRepository.delete(id);
    }
}
