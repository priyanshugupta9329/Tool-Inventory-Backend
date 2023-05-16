package com.example.toolinventorysystem.dto.InputDto;

import com.example.toolinventorysystem.enums.Role;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleAndPermissionDto {
    @NotNull
    private RequestMethod httpMethodType;

    @NotBlank
    private String uri;

    @NotNull
    private List<Role> roles;
}
