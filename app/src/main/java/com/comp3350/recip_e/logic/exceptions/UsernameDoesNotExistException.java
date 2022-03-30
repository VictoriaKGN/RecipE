package com.comp3350.recip_e.logic.exceptions;

public class UsernameDoesNotExistException extends RuntimeException {

    /**
     * Exception indicating a user with a specific username does not exist
     *
     * @param e Additional error information
     */
    public UsernameDoesNotExistException (String e) {
        super(e);
    }
}
