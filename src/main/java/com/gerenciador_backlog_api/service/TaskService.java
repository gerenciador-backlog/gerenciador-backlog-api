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
import java.util.ArrayList;
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

    public List<Task> getTasksRelatedById(String id) {
        List<Task> taksRelated = new ArrayList<>();
        for (String idTaksRelated : this.getTaskById(id).getRelatedTasks()) {
            taksRelated.add(this.getTaskById(idTaksRelated));
        }
        return taksRelated;
    }

    public Task getTaskById(String id) {
        return this.taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));
    }

    public Task createTask(TaskRequestDTO taskRequestDTO) {
        Task task = this.taskMapper.toEntity(taskRequestDTO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setTags(resolveTags(taskRequestDTO.getTags()));

        Task savedTask = taskRepository.save(task);

        processRelatedTasks(savedTask);

        return savedTask;
    }

    public Task updateTask(String id, TaskRequestDTO taskRequestDTO) {

        Task updatedTask = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        clearOldRelations(updatedTask);

        updatedTask.setUpdatedAt(LocalDateTime.now());
        updatedTask.setTitle(taskRequestDTO.getTitle());
        updatedTask.setDescription(taskRequestDTO.getDescription());
        updatedTask.setPriority(taskRequestDTO.getPriority());
        updatedTask.setDueDate(taskRequestDTO.getDueDate());
        updatedTask.setStatus(taskRequestDTO.getStatus());
        updatedTask.setAssignedTo(taskRequestDTO.getAssignedTo());
        updatedTask.setTags(resolveTags(taskRequestDTO.getTags()));

        Task savedTask = taskRepository.save(updatedTask);

        processRelatedTasks(savedTask);

        return savedTask;
    }

    public void deleteTask(String id) {

        Task deletedTask = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        clearOldRelations(deletedTask);

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

    private void processRelatedTasks(Task newTask) {

        List<Task> tasksWithSameTags = taskRepository.findByTagsIn(newTask.getTags());

        List<String> relatedIds = new java.util.ArrayList<>();

        for (Task task : tasksWithSameTags) {

            if (task.getId().equals(newTask.getId())) continue;

            if (hasCommonTag(newTask, task)) {

                relatedIds.add(task.getId());

                if (task.getRelatedTasks() == null) {
                    task.setRelatedTasks(new java.util.ArrayList<>());
                }

                if (!task.getRelatedTasks().contains(newTask.getId())) {
                    task.getRelatedTasks().add(newTask.getId());
                    taskRepository.save(task);
                }
            }
        }

        newTask.setRelatedTasks(relatedIds);
        taskRepository.save(newTask);
    }

    private boolean hasCommonTag(Task t1, Task t2) {

        if (t1.getTags() == null || t2.getTags() == null) return false;

        for (Tag tag1 : t1.getTags()) {
            for (Tag tag2 : t2.getTags()) {
                if (tag1.getName().equalsIgnoreCase(tag2.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private void clearOldRelations(Task task) {

        if (task.getRelatedTasks() == null || task.getRelatedTasks().isEmpty()) {
            return;
        }

        for (String relatedId : task.getRelatedTasks()) {

            Task relatedTask = taskRepository.findById(relatedId).orElse(null);

            if (relatedTask != null && relatedTask.getRelatedTasks() != null) {

                relatedTask.getRelatedTasks().remove(task.getId());
                taskRepository.save(relatedTask);
            }
        }

        task.setRelatedTasks(new java.util.ArrayList<>());
    }
}
