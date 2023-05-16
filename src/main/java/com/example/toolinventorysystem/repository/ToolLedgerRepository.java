package com.example.toolinventorysystem.repository;

import com.example.toolinventorysystem.dto.OutputDto.ToolLedgerOutputDto;
import com.example.toolinventorysystem.models.ToolLedger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ToolLedgerRepository extends MongoRepository<ToolLedger, UUID>, QuerydslPredicateExecutor<ToolLedger> {

    List<ToolLedgerOutputDto> findByUserId(String showUserId);
}
