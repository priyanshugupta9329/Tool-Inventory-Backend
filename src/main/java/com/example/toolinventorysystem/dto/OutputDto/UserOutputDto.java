package com.example.toolinventorysystem.dto.OutputDto;

import com.example.toolinventorysystem.enums.Role;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
@Data
public class UserOutputDto {
    private String title;
    private UUID id;
    private String showUserId;
    private String phoneNumber;
    private String name;
    private String email;
    private Role role;
}
