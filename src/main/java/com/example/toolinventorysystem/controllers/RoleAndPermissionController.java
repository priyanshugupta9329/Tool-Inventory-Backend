package com.example.toolinventorysystem.controllers;

import com.example.toolinventorysystem.dto.InputDto.RoleAndPermissionDto;
import com.example.toolinventorysystem.services.RoleAndPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles-permissions")
@RequiredArgsConstructor
public class RoleAndPermissionController {

    private final RoleAndPermissionService roleAndPermissionService;
    @PostMapping
    public RoleAndPermissionDto createPermission(@RequestBody @Valid RoleAndPermissionDto roleAndPermissionDto){
        return roleAndPermissionService.createPermission(roleAndPermissionDto);
    }

    @GetMapping
    public List<RoleAndPermissionDto> getAllPermission(){
        return roleAndPermissionService.getAllPermission();
    }

}
