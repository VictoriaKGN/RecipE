package com.comp3350.recip_e.logic.exceptions;

public class EmailDoesNotExistException extends RuntimeException {

    /**
     * Exception indicating a user with a specific username does not exist
     *
     * @param e Additional error information
     */
    public EmailDoesNotExistException(String e) {
        super(e);
    }
}

