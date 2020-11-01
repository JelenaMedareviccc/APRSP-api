package com.example.project.exception;
/**
 * This class represents a Exception which should be thrown every time when is tried to list something that doesn't exists in the database
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException(Integer id){
        super("User with id = " + id + " can not be found!");
    }
    public NotFoundException(){super("No entities can be found");}}
