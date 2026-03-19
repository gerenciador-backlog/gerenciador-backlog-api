package com.gerenciador_backlog_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    private String id;

    private List<Tag> tags;

    private String title;

    private String description;

    private String status;

    private String priority;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime dueDate;

    private String assignedTo;
}
