package com.gerenciador_backlog_api.controller;

import com.gerenciador_backlog_api.dto.TaskRequestDTO;
import com.gerenciador_backlog_api.dto.TaskResponseDTO;
import com.gerenciador_backlog_api.mapper.TaskMapper;
import com.gerenciador_backlog_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService,  TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping(path = "/tasks")
    public List<TaskResponseDTO> getTasks() {
        return this.taskService.getTasks().stream().map(taskMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping(path = "/tasks/{id}")
    public TaskResponseDTO getTask(@Valid @PathVariable String id) {
        return this.taskMapper.toDTO(taskService.getTaskById(id));
    }

    @PostMapping(path = "/tasks")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return this.taskMapper.toDTO(taskService.createTask(taskRequestDTO));
    }

    @PutMapping(path = "/tasks/{id}")
    public TaskResponseDTO updateTask(@PathVariable String id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return this.taskMapper.toDTO(taskService.updateTask(id, taskRequestDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/tasks/{id}")
    public void deleteTask(@PathVariable String id) {
        this.taskService.deleteTask(id);
    }

    @GetMapping(path = "/tasks/by-tag")
    public List<TaskResponseDTO> getTasksByTag(@RequestParam String tagName) {
        return this.taskService.getTasksByTag(tagName).stream().map(taskMapper::toDTO).collect(Collectors.toList());
    }

}
