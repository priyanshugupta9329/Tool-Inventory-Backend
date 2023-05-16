package com.example.toolinventorysystem.dto.OutputDto;

import com.example.toolinventorysystem.dto.InputDto.ToolInstanceIdAndNameDto;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class ApprovalDto {
//    private UUID userId;
private UUID userId;
    private UUID machineId;
    private UUID toolLedgerId;
    private String userName;
    private String machineName;
    private Map<String, Integer> toolTypeNameAndUnits;
    private List<ToolInstanceIdAndNameDto> toolsBeingUsed;
}
