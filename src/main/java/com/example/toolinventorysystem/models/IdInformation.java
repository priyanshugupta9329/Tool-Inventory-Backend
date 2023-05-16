package com.example.toolinventorysystem.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class IdInformation extends BaseModel{
    private Long latestUserId;
}
