package com.example.toolinventorysystem.services;

import com.example.toolinventorysystem.models.ToolType;

import java.util.List;
import java.util.UUID;

public interface ToolTypeService {
    public List<ToolType> getAll(String q);
    public ToolType saveToolType(ToolType toolType);

    public ToolType deleteToolType(UUID id);
}
