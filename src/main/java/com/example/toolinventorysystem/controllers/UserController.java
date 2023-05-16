package com.example.toolinventorysystem.controllers;
import com.example.toolinventorysystem.dto.InputDto.LoginInputDto;
import com.example.toolinventorysystem.dto.InputDto.UserInputDto;
import com.example.toolinventorysystem.dto.OutputDto.LoginOutputDto;
import com.example.toolinventorysystem.dto.OutputDto.UserOutputDto;
import com.example.toolinventorysystem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserOutputDto> getAll(@RequestParam(required = false) String search) {
        return userService.getAll(search);
    }

    @PostMapping
    public UserOutputDto saveUser(@Valid @RequestBody UserInputDto user) {
        return userService.saveUser(user);
    }


    @DeleteMapping("/{id}")
    public UserOutputDto deleteById(@PathVariable UUID id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public UserOutputDto updateUser(@PathVariable UUID id, @RequestBody @Valid UserInputDto user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("/{id}")
    public UserOutputDto getUser(@PathVariable UUID id){
        return userService.getUser(id);
    }

//    private final EmailService emailService;
//    @GetMapping("/test")
//    public void sendMail(){
//        emailService.sendMail("TC",emailTemplet, "1rn19cs175.varunthantry@gmail.com");
//    }

    //if user returns only few tools and other tools and machine is not yet returned how to log that to tool ledger as we have date of request machine and set of tools in that particular row
//    @PostMapping("/{id}/return")
//    public void returnTool(@PathVariable UUID id){userService.returnTool(id);}

    @PostMapping("/login")
    public LoginOutputDto login(@RequestBody LoginInputDto loginInputDto) {
        return userService.login(loginInputDto);
    }

    @GetMapping("/me")
    public UserOutputDto userMe(){
        return userService.userMe();
    }

}
