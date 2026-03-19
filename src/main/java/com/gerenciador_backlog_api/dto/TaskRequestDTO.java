package com.gerenciador_backlog_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TaskRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String priority;

    @NotBlank
    private String status;

    @NotNull
    private LocalDateTime dueDate;

    @NotBlank
    private String assignedTo;

    private List<TagRequestDTO> tags;
}
