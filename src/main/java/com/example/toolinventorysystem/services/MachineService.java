package com.example.toolinventorysystem.services;

import com.example.toolinventorysystem.dto.InputDto.MachineInputDto;
import com.example.toolinventorysystem.dto.OutputDto.MachineOutputDto;
import com.example.toolinventorysystem.dto.OutputDto.ToolTypeOutputDto;

import java.util.List;
import java.util.UUID;


public interface MachineService {

    public List<MachineOutputDto> getAll(String q);
    public MachineOutputDto saveMachine(MachineInputDto machine);
    public MachineOutputDto deleteMachine(UUID id);
    List<ToolTypeOutputDto> getCategoryByMachineId(UUID machineId);

}
