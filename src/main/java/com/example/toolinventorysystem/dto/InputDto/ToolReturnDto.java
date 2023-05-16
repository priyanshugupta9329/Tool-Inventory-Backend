package com.example.toolinventorysystem.dto.InputDto;

import lombok.Data;

import java.util.Map;
import java.util.UUID;
@Data
public class ToolReturnDto {
    private UUID machineId;
    private Map<UUID, Integer> toolUsed;
    private UUID toolLedgerId;
    private UUID approvalId;
}
