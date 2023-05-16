package com.example.toolinventorysystem.controllers;

import com.example.toolinventorysystem.models.ToolType;
import com.example.toolinventorysystem.services.ToolTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/toolTypes")
@RequiredArgsConstructor
public class ToolTypeController {
    private final ToolTypeService toolTypeService;


    @PostMapping
    public ToolType saveToolType(@RequestBody @Valid ToolType toolType) {
        return toolTypeService.saveToolType(toolType);
    }

    @GetMapping
    public List<ToolType> getAll(@RequestParam(required = false) String q) {
        return toolTypeService.getAll(q);
    }

    @DeleteMapping("/{id}")
    public ToolType deleteById(@PathVariable UUID id) {
        return toolTypeService.deleteToolType(id);
    }
}
