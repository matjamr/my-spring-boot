package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.*;
import efs.task.todoapp.init.commons.error.HttpStatusError;
import efs.task.todoapp.init.commons.error.ServiceError;
import efs.task.todoapp.init.commons.http.HttpStatus;
import efs.task.todoapp.model.pojos.DataDto;
import efs.task.todoapp.model.pojos.DataResponseDto;
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
    public List<DataResponseDto> getTasks(@Principal UserDto userDto) {
        return dataService.getTasks(userDto);
    }

    @GetMapping(path = "/task/{id}")
    public DataResponseDto getTaskById(@Principal UserDto userDto, @PathVariable String id) {
        try {
            return dataService.getTaskById(userDto, id);
        } catch (ServiceError e) {
            throw new HttpStatusError(e.getMessage(), e.getHttpStatus());
        }
    }

    @PutMapping(path = "/task/{id}")
    public DataResponseDto updateTaskById(@RequiredBody DataDto dataDto, @Principal UserDto userDto, @PathVariable String id) {
        try {
            return dataService.putTaskById(dataDto, userDto, id);
        } catch (ServiceError e) {
            throw new HttpStatusError(e.getMessage(), e.getHttpStatus());
        }
    }

    @DeleteMapping(path = "/task/{id}")
    public void deleteTaskById(@Principal UserDto userDto, @PathVariable String id) {
        try {
            dataService.deleteTaskById(userDto, id);
        } catch (ServiceError e) {
            throw new HttpStatusError(e.getMessage(), e.getHttpStatus());
        }
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
