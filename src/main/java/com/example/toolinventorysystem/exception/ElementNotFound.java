package com.example.toolinventorysystem.exception;

import lombok.Getter;

@Getter
public class ElementNotFound extends RuntimeException{
    public ElementNotFound(String message){
        super(message);

    }
}
