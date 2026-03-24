package com.gerenciador_backlog_api.service;

import com.gerenciador_backlog_api.dto.TagRequestDTO;
import com.gerenciador_backlog_api.exception.NotFoundException;
import com.gerenciador_backlog_api.mapper.TagMapper;
import com.gerenciador_backlog_api.model.Tag;
import com.gerenciador_backlog_api.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public Tag getTag(String id) {
        return this.tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag not found"));
    }

    public Tag getTagByName(String name) {
        return this.tagRepository.findByName(name).orElseThrow(() -> new NotFoundException("Tag not found"));
    }

    public List<Tag> getTags() {
        return this.tagRepository.findAll();
    }

    public Tag create(TagRequestDTO tagRequestDTO) {
        Tag tag = this.tagMapper.ToEntity(tagRequestDTO);
        tag.setCreatedAt(LocalDateTime.now());
        tag.setUpdatedAt(LocalDateTime.now());
        return this.tagRepository.save(tag);
    }

    public Tag update(String id, TagRequestDTO tagRequestDTO) {
        Tag tag = this.tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag not found"));
        tag.setUpdatedAt(LocalDateTime.now());
        tag.setName(tagRequestDTO.getName());
        tag.setColor(tagRequestDTO.getColor());
        return this.tagRepository.save(tag);
    }

    public void delete(String id) {
        Tag tag = this.tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag not found"));
        this.tagRepository.deleteById(tag.getId());
    }
}
