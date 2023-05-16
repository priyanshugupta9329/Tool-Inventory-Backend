package com.example.toolinventorysystem.models;

import com.example.toolinventorysystem.enums.ApprovalStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@Document
public class Approval extends BaseModel {
    private UUID userId;
    private UUID machineId;
    private UUID toolLedgerId;
    private String userName;
    private String machineName;
//    private String toolTypeName;
//    private UUID toolTypeId;
//    private Integer units;
    private Map<UUID,Integer> toolTypeAndUnits;
    private Map<String, Integer> toolTypeNameAndUnits;
//    private List<UUID> toolIds;
    private Map<UUID, String> toolIdAndName;
    private LocalDateTime requestAt;
    private LocalDateTime approvedAt;
    private ApprovalStatus status;
}
