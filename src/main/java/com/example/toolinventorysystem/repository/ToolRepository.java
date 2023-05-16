package com.example.toolinventorysystem.repository;
import com.example.toolinventorysystem.models.Tool;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToolRepository extends MongoRepository<Tool, UUID> {
    List<Tool> findByIdIn(List<UUID> toolInstance);
    Tool findByIdIn(UUID tool);
}
