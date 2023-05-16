package com.example.toolinventorysystem.repository;
import com.example.toolinventorysystem.models.Approval;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ApprovalRepository extends MongoRepository<Approval, UUID> {
}
