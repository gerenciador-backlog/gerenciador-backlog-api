package com.gerenciador_backlog_api.service;

import com.gerenciador_backlog_api.dto.TagRequestDTO;
import com.gerenciador_backlog_api.dto.TaskRequestDTO;
import com.gerenciador_backlog_api.exception.NotFoundException;
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
    private final TagService tagService;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, TagService tagService, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(String id) {
        return this.taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
    }

    public Task createTask(TaskRequestDTO taskRequestDTO) {
        Task task = this.taskMapper.toEntity(taskRequestDTO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setTags(resolveTags(taskRequestDTO.getTags()));
        return taskRepository.save(task);
    }

    public Task updateTask(String id, TaskRequestDTO taskRequestDTO) {
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        updatedTask.setUpdatedAt(LocalDateTime.now());
        updatedTask.setTitle(taskRequestDTO.getTitle());
        updatedTask.setDescription(taskRequestDTO.getDescription());
        updatedTask.setPriority(taskRequestDTO.getPriority());
        updatedTask.setDueDate(taskRequestDTO.getDueDate());
        updatedTask.setStatus(taskRequestDTO.getStatus());
        updatedTask.setAssignedTo(taskRequestDTO.getAssignedTo());
        updatedTask.setTags(resolveTags(taskRequestDTO.getTags()));
        return taskRepository.save(updatedTask);
    }

    public void deleteTask(String id) {
        Task deletedTask = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
        taskRepository.delete(deletedTask);
    }

    public List<Task> getTasksByTag(String tagName) {
        return taskRepository.findByTagsName(tagName);
    }

    public Tag findOrCreate(TagRequestDTO tagRequestDTO) {
        return tagRepository.findByName(tagRequestDTO.getName())
                .orElseGet(() -> this.tagService.create(tagRequestDTO));
    }

    private List<Tag> resolveTags(List<TagRequestDTO> tagDTOs) {
        if (tagDTOs.isEmpty()) {
            return Collections.emptyList();
        }
        return tagDTOs.stream()
                .map(this::findOrCreate)
                .toList();
    }
}
