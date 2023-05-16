package com.example.toolinventorysystem.repository;

import com.example.toolinventorysystem.models.Machine;
import com.example.toolinventorysystem.models.ToolLedger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface MachineRepository extends MongoRepository<Machine, UUID>, QuerydslPredicateExecutor<Machine> {

}
