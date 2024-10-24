package com.example.springrestapi.exceptions;

public class UserExistsException extends RuntimeException{

    public UserExistsException(String errorMessage){
        super(errorMessage);
    }
}
