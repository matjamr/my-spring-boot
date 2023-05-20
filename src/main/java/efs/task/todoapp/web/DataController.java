package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.*;
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

    @GetMapping(path = "/task", secured = true)
    public List<DataDto> getTasks(UserDto userDto) {
        return dataService.getTasks();
    }

    @GetMapping(path = "/todo/task/{id}", secured = true)
    public String getTaskById(UserDto userDto) {
        return "121231231!@#!@#!@#! GETTT";
    }

    @PutMapping(path = "/todo/task/{id}")
    public String updateTaskById(UserDto userDto) {
        return "121231231!@#!@#!@#! UDPATE";
    }

    @DeleteMapping(path = "/todo/task/{id}")
    public String deleteTaskById(UserDto userDto) {
        return "121231231!@#!@#!@#! DELETE";
    }

    @PostMapping(path = "/task")
    public UUIDResponse saveData(@RequiredBody DataDto dataDto, @Principal UserDto userDto) {
        return dataService.save(dataDto);
    }

}
