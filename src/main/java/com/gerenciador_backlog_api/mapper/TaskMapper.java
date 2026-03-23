package com.gerenciador_backlog_api.mapper;

import com.gerenciador_backlog_api.dto.TaskRequestDTO;
import com.gerenciador_backlog_api.dto.TaskResponseDTO;
import com.gerenciador_backlog_api.model.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.typeMap(TaskRequestDTO.class, Task.class)
                .addMappings(mapper -> mapper.skip(Task::setTags));
    }

    public TaskResponseDTO toDTO(Task task) {
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    public Task toEntity(TaskRequestDTO dto) {
        return modelMapper.map(dto, Task.class);
    }
}

