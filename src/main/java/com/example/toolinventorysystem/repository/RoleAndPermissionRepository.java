package com.example.toolinventorysystem.repository;

import com.example.toolinventorysystem.models.RoleAndPermission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleAndPermissionRepository extends MongoRepository<RoleAndPermission, UUID> {
     Optional<RoleAndPermission> findByHttpMethodTypeAndUri(RequestMethod httpMethod, String uri);
}
