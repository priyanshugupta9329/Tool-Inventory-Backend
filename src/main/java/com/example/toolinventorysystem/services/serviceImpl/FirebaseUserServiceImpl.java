package com.example.toolinventorysystem.services.serviceImpl;

import com.example.toolinventorysystem.dto.InputDto.FirebaseUserInputDto;
import com.example.toolinventorysystem.services.FirebaseUserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Log4j2
@Service
public class FirebaseUserServiceImpl implements FirebaseUserService {
    @Override
    public UserRecord createUserInFireBase(FirebaseUserInputDto input) {
        log.debug("Trying to create user in firebase");
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(input.getEmail())
                .setPassword(input.getPassword())
                .setDisplayName(input.getName())
                .setDisabled(false);
        try {
            return FirebaseAuth.getInstance().createUser(request);
        } catch (FirebaseAuthException e) {
            log.error("Something went wrong : {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    public UserRecord updateUser(String id, FirebaseUserInputDto input) {
        log.debug("Trying to update email of user in firebase");
        UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(id)
                .setEmail(input.getEmail())
                .setDisplayName(input.getName())
//                .setPassword(input.getPassword())
                .setDisabled(false);
        try {
            return FirebaseAuth.getInstance().updateUserAsync(request).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Something went wrong : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(String id) {
        try {
            FirebaseAuth.getInstance().deleteUser(id);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
    }

}
