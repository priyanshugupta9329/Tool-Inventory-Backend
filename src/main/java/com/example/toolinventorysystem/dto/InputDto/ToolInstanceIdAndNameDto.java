package com.example.toolinventorysystem.dto.InputDto;

import lombok.*;

import java.util.UUID;

@Data
public class ToolInstanceIdAndNameDto {
    private UUID toolId;
    private String toolTypeName;
}
