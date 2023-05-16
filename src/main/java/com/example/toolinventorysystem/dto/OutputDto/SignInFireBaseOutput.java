package com.example.toolinventorysystem.dto.OutputDto;

import lombok.Data;

@Data
public class SignInFireBaseOutput {
    private String idToken;

    private String email;

    private String refreshToken;

    private String expiresIn;

    private String localId;

    private boolean registered;
}
