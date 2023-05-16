package com.example.toolinventorysystem.dto.InputDto;

import lombok.Data;

import java.util.UUID;

@Data
public class ToolDto {
    private UUID toolType;
    private String toolTypeName;
    private Float lifecycle;
    private Integer numberOfResharpen;
}
