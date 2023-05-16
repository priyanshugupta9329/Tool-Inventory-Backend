package com.example.toolinventorysystem.dto.InputDto;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class ToolInput {
   private UUID machineId;
   private Map<UUID, Integer> tools;

}
