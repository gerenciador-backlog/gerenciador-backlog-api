package com.gerenciador_backlog_api.repository;

import com.gerenciador_backlog_api.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByTagsName(String tagName);
}
