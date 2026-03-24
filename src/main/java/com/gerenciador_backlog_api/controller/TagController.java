package com.gerenciador_backlog_api.controller;

import com.gerenciador_backlog_api.dto.TagRequestDTO;
import com.gerenciador_backlog_api.dto.TagResponseDTO;
import com.gerenciador_backlog_api.mapper.TagMapper;
import com.gerenciador_backlog_api.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    public TagController(TagService tagService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @GetMapping(path = "/tags")
    public List<TagResponseDTO> getTags() {
        return this.tagService.getTags().stream().map(tagMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping(path = "/tags/{id}")
    public TagResponseDTO getTagById(@PathVariable String id) {
        return this.tagMapper.toDTO(this.tagService.getTag(id));
    }

    @PostMapping(path = "/tags")
    public TagResponseDTO createTag(@RequestBody TagRequestDTO tagRequestDTO) {
        return this.tagMapper.toDTO(this.tagService.create(tagRequestDTO));
    }

    @PutMapping(path = "/tags/{id}")
    public TagResponseDTO updateTag(@PathVariable String id, @RequestBody TagRequestDTO tagRequestDTO) {
        return this.tagMapper.toDTO(this.tagService.update(id, tagRequestDTO));
    }

    @DeleteMapping(path = "/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTag(@PathVariable String id) {
        this.tagService.delete(id);
    }
}
