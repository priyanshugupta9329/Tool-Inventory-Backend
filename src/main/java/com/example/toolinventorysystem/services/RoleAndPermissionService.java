package com.example.toolinventorysystem.services;

import com.example.toolinventorysystem.dto.InputDto.RoleAndPermissionDto;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface RoleAndPermissionService {
     RoleAndPermissionDto createPermission(RoleAndPermissionDto roleAndPermissionDto);

     List<RoleAndPermissionDto> getAllPermission();

     RoleAndPermissionDto getAllPermissionByMethodAndUrl(RequestMethod httpMethod, String uri);
}
