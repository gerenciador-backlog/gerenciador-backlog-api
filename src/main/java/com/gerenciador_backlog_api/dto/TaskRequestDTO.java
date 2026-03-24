package com.gerenciador_backlog_api.dto;

import com.gerenciador_backlog_api.enums.Priority;
import com.gerenciador_backlog_api.enums.Status;
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

    @NotNull
    private Priority priority;

    @NotNull
    private Status status;

    @NotNull
    private LocalDateTime dueDate;

    @NotBlank
    private String assignedTo;

    @NotNull
    private List<TagRequestDTO> tags;

}
