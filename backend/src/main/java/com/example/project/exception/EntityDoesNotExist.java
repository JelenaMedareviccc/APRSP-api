package com.example.project.exception;

public class EntityDoesNotExist extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityDoesNotExist(Integer id) {
		
		super("Entity with Id: " + id  + " does not exist.");
		
		
	}
}
