package com.example.springrestapi.exceptions;

public class RoleExistsException extends RuntimeException{
    public RoleExistsException(String errorMessage){
        super(errorMessage);
    }
}
