package efs.task.todoapp.mappers;

import efs.task.todoapp.init.annotationExecutors.annotations.Component;
import efs.task.todoapp.model.entity.TaskEntity;
import efs.task.todoapp.model.pojos.DataResponseDto;

@Component
public class Mapper {

    public DataResponseDto mapTo(TaskEntity task) {
        return DataResponseDto.builder()
                .id(task.getId().toString())
                .description(task.getDescription())
                .due(task.getDue())
                .build();
    }
}
