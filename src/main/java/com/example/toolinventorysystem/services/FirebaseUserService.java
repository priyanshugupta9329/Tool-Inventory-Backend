package com.example.toolinventorysystem.services;

import com.example.toolinventorysystem.dto.InputDto.FirebaseUserInputDto;
import com.google.firebase.auth.UserRecord;

public interface FirebaseUserService {
    UserRecord createUserInFireBase(FirebaseUserInputDto input);
    UserRecord updateUser(String id, FirebaseUserInputDto input);
    void deleteUser(String id);
}
