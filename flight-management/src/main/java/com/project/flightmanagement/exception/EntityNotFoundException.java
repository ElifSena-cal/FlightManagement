package com.project.flightmanagement.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long aircraftId,String entityName) {
        super(entityName+" with ID " + aircraftId + " not found");
    }
}
