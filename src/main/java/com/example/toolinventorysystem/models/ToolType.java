package com.example.toolinventorysystem.models;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document
@Setter
@Getter
public class ToolType extends BaseModel{
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\s]*$")
    private String name;

}
