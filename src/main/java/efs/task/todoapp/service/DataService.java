package efs.task.todoapp.service;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.init.annotationExecutors.annotations.Service;
import efs.task.todoapp.init.commons.error.ServiceError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.model.entity.TaskEntity;
import efs.task.todoapp.model.pojos.DataDto;
import efs.task.todoapp.model.pojos.UUIDResponse;
import efs.task.todoapp.repository.TaskRepository;
import efs.task.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@Component
@RequiredArgsConstructor
public class DataService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public void test() {

    }

    public UUIDResponse save(DataDto dataDto) {

        if(isNull(dataDto.getDescription()) || isNull(dataDto.getDue())) {
            throw new ServiceError("Invalid data data", HttpStatus.BAD_REQUEST);
        }

        var created = taskRepository.save(TaskEntity.builder()
                        .id(UUID.randomUUID())
                        .description(dataDto.getDescription())
                        .due(dataDto.getDue())
                .build());

        return UUIDResponse.builder()
                .id(created.toString())
                .build();
    }

    public List<DataDto> getTasks() {
        return taskRepository
                .query(taskEntity -> true).stream()
                .map(taskEntity -> DataDto.builder()
                        .due(taskEntity.getDue())
                        .description(taskEntity.getDescription())
                        .build()).toList();
    }
}
