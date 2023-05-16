package com.example.toolinventorysystem.dto.InputDto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Data
public class UserInputDto {
//    private String showUserId;
    private String title;
    @Pattern(regexp = "\\d{10}")
    private String phoneNumber;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\s]*$")
    private String name;
    @Email
    private String email;
//    private List<UUID> currentMachine;
//    private Role role;
}
