package com.example.toolinventorysystem.services.serviceImpl;

import com.example.toolinventorysystem.dto.InputDto.RoleAndPermissionDto;
import com.example.toolinventorysystem.exception.ElementNotFound;
import com.example.toolinventorysystem.models.RoleAndPermission;
import com.example.toolinventorysystem.repository.RoleAndPermissionRepository;
import com.example.toolinventorysystem.services.RoleAndPermissionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleAndPermissionServiceImpl implements RoleAndPermissionService {

    private final RoleAndPermissionRepository roleAndPermissionRepository;
    private final ModelMapper modelMapper;


    @Override
    public RoleAndPermissionDto createPermission(RoleAndPermissionDto roleAndPermissionDto) {
        RoleAndPermission roleAndPermission = modelMapper.map(roleAndPermissionDto, RoleAndPermission.class);
        roleAndPermission.setId(UUID.randomUUID());
        roleAndPermission=roleAndPermissionRepository.save(roleAndPermission);
        return modelMapper.map(roleAndPermission,RoleAndPermissionDto.class);
    }

    @Override
    public List<RoleAndPermissionDto> getAllPermission() {
        List<RoleAndPermission> roleAndPermission=roleAndPermissionRepository.findAll();
        return roleAndPermission.stream().map(role->modelMapper.map(role,RoleAndPermissionDto.class)).toList();
    }

    @Override
    public RoleAndPermissionDto getAllPermissionByMethodAndUrl(RequestMethod httpMethod, String uri) {
        RoleAndPermission roleAndPermission=roleAndPermissionRepository.findByHttpMethodTypeAndUri(httpMethod,uri).orElseThrow(()-> new ElementNotFound("no such permissions with this method and uri"));
        return modelMapper.map(roleAndPermission,RoleAndPermissionDto.class);
    }
}
