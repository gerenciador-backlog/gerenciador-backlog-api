package com.gerenciador_backlog_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    // todo: validacao em enum 'low' | 'medium' | 'high'
    @NotBlank
    private String priority;

    // todo: validacao em enum 'todo' | 'in_progress' | 'done'
    @NotBlank
    private String status;

    @NotNull
    private LocalDateTime dueDate;

    @NotBlank
    private String assignedTo;

    @NotNull
    private List<TagRequestDTO> tags;

}
