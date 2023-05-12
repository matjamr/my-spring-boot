package efs.task.todoapp.web;

import efs.task.todoapp.init.annotationExecutors.annotations.*;
import efs.task.todoapp.model.pojos.DataDto;
import efs.task.todoapp.model.pojos.UUIDResponse;
import efs.task.todoapp.service.DataService;
import lombok.RequiredArgsConstructor;

@Component
@RestController
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @GetMapping(path = "/todo/task", secured = true)
    public String getTasks() {
        return "121231231!@#!@#!@#!";
    }

    @GetMapping(path = "/todo/task/{id}")
    public String getTaskById() {
        return "121231231!@#!@#!@#! GETTT";
    }

    @PutMapping(path = "/todo/task/{id}")
    public String updateTaskById() {
        return "121231231!@#!@#!@#! UDPATE";
    }

    @DeleteMapping(path = "/todo/task/{id}")
    public String deleteTaskById() {
        return "121231231!@#!@#!@#! DELETE";
    }

    @PostMapping(path = "/task")
    public UUIDResponse test2Method(@RequiredBody DataDto dataDto) {
        return dataService.save(dataDto);
    }

}
