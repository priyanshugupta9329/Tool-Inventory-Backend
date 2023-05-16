package com.example.toolinventorysystem.dto.InputDto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class FirebaseUserInputDto {
    @NotBlank
    private String name;
    @Email
    private String email;
    private String password;

}
