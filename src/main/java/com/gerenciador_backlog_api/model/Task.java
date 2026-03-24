package com.gerenciador_backlog_api.model;

import com.gerenciador_backlog_api.enums.Priority;
import com.gerenciador_backlog_api.enums.Status;
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

    private Status status;

    private Priority priority;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime dueDate;

    private String assignedTo;

    // todo: pensar em como adicionar:  relatedTasks: string[] --> IDs de tarefas correlacionadas (FE06), similarTasks: string[] --> IDs de tarefas semelhantes (FE05)

    // todo: validar tags existentes / criar validação personalizada

    // todo: dashboard

}
