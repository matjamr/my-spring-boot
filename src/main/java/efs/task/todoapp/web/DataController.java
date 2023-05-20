package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.*;
import efs.task.todoapp.init.commons.error.HttpStatusError;
import efs.task.todoapp.init.commons.error.ServiceError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.model.entity.TaskEntity;
import efs.task.todoapp.model.pojos.DataDto;
import efs.task.todoapp.model.pojos.UUIDResponse;
import efs.task.todoapp.model.pojos.UserDto;
import efs.task.todoapp.service.DataService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RestController
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @GetMapping(path = "/task")
    public List<TaskEntity> getTasks(@Principal UserDto userDto) {
        return dataService.getTasks(userDto);
    }

    @GetMapping(path = "/task/{id}")
    public String getTaskById(@Principal UserDto userDto, @PathVariable String id) {
        return "121231231!@#!@#!@#! GETTT";
    }

    @PutMapping(path = "/task/{id}")
    public String updateTaskById(@Principal UserDto userDto, @PathVariable String id) {
        return "121231231!@#!@#!@#! UDPATE";
    }

    @DeleteMapping(path = "/task/{id}")
    public String deleteTaskById(@Principal UserDto userDto, @PathVariable String id) {
        return "121231231!@#!@#!@#! DELETE";
    }


    @PostMapping(path = "/task")
    @Response(status = HttpStatus.CREATED)
    public UUIDResponse saveData(@RequiredBody DataDto dataDto, @Principal UserDto userDto) {
        try {
            return dataService.save(dataDto, userDto);
        } catch (ServiceError e) {
            throw new HttpStatusError(e.getMessage(), e.getHttpStatus());
        }
    }

}
