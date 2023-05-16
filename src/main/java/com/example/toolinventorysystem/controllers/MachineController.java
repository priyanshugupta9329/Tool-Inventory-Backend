package com.example.toolinventorysystem.controllers;

import com.example.toolinventorysystem.dto.InputDto.MachineInputDto;
import com.example.toolinventorysystem.dto.OutputDto.MachineOutputDto;
import com.example.toolinventorysystem.dto.OutputDto.ToolTypeOutputDto;
import com.example.toolinventorysystem.services.MachineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/machines")
@AllArgsConstructor
public class MachineController {
    private final MachineService machineService;

    @PostMapping
    public MachineOutputDto saveMachine(@RequestBody @Valid MachineInputDto machine) {
        return machineService.saveMachine(machine);
    }

    @GetMapping
    public List<MachineOutputDto> getAll(@RequestParam(required = false) String q) {
        return machineService.getAll(q);
    }

    @DeleteMapping("/{id}")
    public MachineOutputDto deleteById(@PathVariable UUID id) {

        return machineService.deleteMachine(id);
    }
    @GetMapping("/{id}/available-toolTypes")
    public List<ToolTypeOutputDto> getToolTypeByMachineId(@PathVariable UUID id){
        return machineService.getCategoryByMachineId(id);
    }

}

