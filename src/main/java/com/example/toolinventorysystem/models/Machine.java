package com.example.toolinventorysystem.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
@Document
public class Machine extends BaseModel {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private Map<UUID,List<UUID>> toolIds;
//    private Map<UUID, Integer> toolTypeInstances; //toolType and units
    private Boolean available;

}
