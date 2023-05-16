package com.example.toolinventorysystem.models;

import com.example.toolinventorysystem.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document
@ToString
public class User extends BaseModel{
    private String title;
    @Pattern(regexp = "\\d{10}")
    private String phoneNumber;
    @NotNull
    private Long showUserId;
    private String firebaseId;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\s]*$")
    private String name;
    @Email
    private String email;
    private List<UUID> currentMachine;
    private Role role;
    private String password;
}
