package com.example.toolinventorysystem.services;

import com.example.toolinventorysystem.dto.InputDto.LoginInputDto;
import com.example.toolinventorysystem.dto.InputDto.UserInputDto;
import com.example.toolinventorysystem.dto.OutputDto.LoginOutputDto;
import com.example.toolinventorysystem.dto.OutputDto.UserOutputDto;
import com.example.toolinventorysystem.models.User;

import java.util.List;
import java.util.UUID;


public interface UserService {

    public List<UserOutputDto> getAll(String q);
    public UserOutputDto saveUser(UserInputDto user);
    public UserOutputDto getUser(UUID id);
    public UserOutputDto deleteUser(UUID id);

    public UserOutputDto updateUser(UUID id,UserInputDto user);

    public LoginOutputDto login(LoginInputDto loginInputDto);

    User getByFirebaseId(String uid);

    UserOutputDto userMe();


//    public void returnTool(UUID id);
}
