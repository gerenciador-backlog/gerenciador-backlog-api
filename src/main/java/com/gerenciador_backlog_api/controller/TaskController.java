package com.gerenciador_backlog_api.controller;

import com.gerenciador_backlog_api.dto.TaskRequestDTO;
import com.gerenciador_backlog_api.dto.TaskResponseDTO;
import com.gerenciador_backlog_api.service.TaskService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "/tasks")
    public List<TaskResponseDTO> getTasks() {
        return this.taskService.getTasks();
    }

    @GetMapping(path = "/tasks/{id}")
    public TaskResponseDTO getTask(@Valid @PathVariable String id) {
        return this.taskService.getTaskById(id);
    }

    @PostMapping(path = "/tasks")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return this.taskService.createTask(taskRequestDTO);
    }

    @PutMapping(path = "/tasks/{id}")
    public TaskResponseDTO updateTask(@PathVariable String id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return this.taskService.updateTask(id,taskRequestDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/tasks/{id}")
    public void deleteTask(@PathVariable String id) {
        this.taskService.deleteTask(id);
    }

}
