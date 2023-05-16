package com.example.toolinventorysystem.dto.OutputDto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Data
public class ToolLedgerOutputDto {
//    private UUID userId;
    @NotNull
    private String showUserId;
    private String userName;
//    private UUID machineId;
//    private List<UUID> toolId;
    private UUID approvalId;
    private UUID machineId;
    @NotBlank
    private String machineName;
    @NotBlank
    private Map<String, Integer> toolTypeNameAndUnits;
    private Boolean isInUse;
    private LocalDateTime startDateTime;
    private LocalDateTime returnDateTime;
}
