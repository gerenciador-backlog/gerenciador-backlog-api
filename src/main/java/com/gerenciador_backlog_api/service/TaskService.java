package com.gerenciador_backlog_api.service;

import com.gerenciador_backlog_api.dto.TagRequestDTO;
import com.gerenciador_backlog_api.dto.TaskRequestDTO;
import com.gerenciador_backlog_api.dto.TaskResponseDTO;
import com.gerenciador_backlog_api.mapper.TaskMapper;
import com.gerenciador_backlog_api.model.Tag;
import com.gerenciador_backlog_api.model.Task;
import com.gerenciador_backlog_api.repository.TagRepository;
import com.gerenciador_backlog_api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TagRepository tagRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponseDTO> getTasks() {
        return taskRepository.findAll().stream().map(taskMapper::toDTO).toList();
    }

    public TaskResponseDTO getTaskById(String id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        return taskMapper.toDTO(task);
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Task task = this.taskMapper.toEntity(taskRequestDTO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setTags(resolveTags(taskRequestDTO.getTags()));
        return taskMapper.toDTO(taskRepository.save(task));
    }

    public TaskResponseDTO updateTask(String id, TaskRequestDTO taskRequestDTO) {
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        updatedTask.setUpdatedAt(LocalDateTime.now());
        updatedTask.setTitle(taskRequestDTO.getTitle());
        updatedTask.setDescription(taskRequestDTO.getDescription());
        updatedTask.setPriority(taskRequestDTO.getPriority());
        updatedTask.setDueDate(taskRequestDTO.getDueDate());
        updatedTask.setStatus(taskRequestDTO.getStatus());
        updatedTask.setAssignedTo(taskRequestDTO.getAssignedTo());
        updatedTask.setTags(resolveTags(taskRequestDTO.getTags()));

        return taskMapper.toDTO(taskRepository.save(updatedTask));
    }

    public void deleteTask(String id) {
        Task deletedTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        taskRepository.delete(deletedTask);
    }

    public List<TaskResponseDTO> getTasksByTag(String tagName) {
        return taskRepository.findByTagsName(tagName)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    private List<Tag> resolveTags(List<TagRequestDTO> tagDTOs) {
        if (tagDTOs == null || tagDTOs.isEmpty()) {
            return Collections.emptyList();
        }
        return tagDTOs.stream()
                .map(dto -> tagRepository.findByName(dto.getName())
                        .orElseGet(() -> tagRepository.save(new Tag(null, dto.getName()))))
                .toList();
    }
}
