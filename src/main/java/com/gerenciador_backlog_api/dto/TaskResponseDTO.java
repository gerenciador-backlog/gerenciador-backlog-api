package com.gerenciador_backlog_api.dto;

import com.gerenciador_backlog_api.enums.Priority;
import com.gerenciador_backlog_api.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {

    private String id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    private String assignedTo;
    private List<TagResponseDTO> tags;
    private List<String> relatedTasks;
    
}
