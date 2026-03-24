package com.gerenciador_backlog_api.mapper;

import com.gerenciador_backlog_api.dto.TagRequestDTO;
import com.gerenciador_backlog_api.dto.TagResponseDTO;
import com.gerenciador_backlog_api.model.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    private final ModelMapper modelMapper;

    public TagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TagResponseDTO toDTO(Tag tag) {
        return modelMapper.map(tag, TagResponseDTO.class);
    }

    public Tag ToEntity(TagRequestDTO tagRequestDTO) {
        return modelMapper.map(tagRequestDTO, Tag.class);
    }
}
