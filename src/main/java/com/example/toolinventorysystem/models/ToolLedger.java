package com.example.toolinventorysystem.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;



@Getter
@Setter
@Document
public class ToolLedger extends BaseModel{

    private UUID userId;
    @NotBlank
    private String username;
    @NotNull
    private Long showUserId;
    private UUID machineId;
    @NotBlank
    private String machineName;
    private List<UUID> toolIds;
    private Map<String, Integer> toolTypeNameAndUnits;
//    private Integer units;
    private UUID approvalId;
    private Boolean isInUse;
    private LocalDateTime startDateTime;
    private LocalDateTime returnDateTime;

}
