package com.example.toolinventorysystem.dto.OutputDto;

import lombok.Data;

import java.util.UUID;
@Data
public class MachineOutputDto {
    private UUID id;
    private String name;
    private String description;
    private Boolean available;

}
