package com.example.toolinventorysystem.dto.InputDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class MachineInputDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
