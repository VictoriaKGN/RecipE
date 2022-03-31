package com.comp3350.recip_e.logic.exceptions;

public class IncorrectPasswordException extends RuntimeException{

    /**
     * Exception indicating an incorrect password
     *
     * @param e Additional error information
     */
    public IncorrectPasswordException (String e) {
        super(e);
    }
}

