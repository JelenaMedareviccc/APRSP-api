package com.example.project.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String username){
        super("User with username = " + username + " already exists!");
    }

}
