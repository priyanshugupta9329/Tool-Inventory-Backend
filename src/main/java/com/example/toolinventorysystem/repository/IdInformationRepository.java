package com.example.toolinventorysystem.repository;

import com.example.toolinventorysystem.models.IdInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IdInformationRepository extends MongoRepository<IdInformation, UUID> {
}
