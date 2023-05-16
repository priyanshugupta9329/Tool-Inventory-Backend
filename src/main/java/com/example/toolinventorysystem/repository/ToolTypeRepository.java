package com.example.toolinventorysystem.repository;

import com.example.toolinventorysystem.models.ToolType;
import com.example.toolinventorysystem.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ToolTypeRepository extends MongoRepository<ToolType, UUID>, QuerydslPredicateExecutor<ToolType> {
    List<ToolType> findByIdIn(List<UUID> uuids);
}
