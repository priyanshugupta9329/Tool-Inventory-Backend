package com.example.toolinventorysystem.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class BaseModel {
    @Id
    protected UUID id;
//    @CreatedDate
//    protected LocalDateTime createdAt;
//    @CreatedBy
//    protected String createdBy;
//    @LastModifiedDate
//    protected LocalDateTime updatedAt;
//    @LastModifiedBy
//    protected String updatedBy;
//    protected Boolean isArchive = Boolean.FALSE;
}
