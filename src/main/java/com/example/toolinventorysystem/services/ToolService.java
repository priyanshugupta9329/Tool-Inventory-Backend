package com.example.toolinventorysystem.services;

import com.example.toolinventorysystem.dto.InputDto.ToolDto;
import com.example.toolinventorysystem.dto.InputDto.ToolInput;
import com.example.toolinventorysystem.dto.InputDto.ToolReturnDto;

import java.util.List;

public interface ToolService {
    List<ToolDto> addTools(ToolInput input);
    public void returnTools(ToolReturnDto toolReturnDto);
}
