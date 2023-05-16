package com.example.toolinventorysystem.dto.OutputDto;

import lombok.Data;

@Data
public class LoginOutputDto {
    private String accessToken;

    private String refreshToken;

    private String expiresIn;

    private String username;

}
