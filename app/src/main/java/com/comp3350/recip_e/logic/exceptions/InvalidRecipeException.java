package com.comp3350.recip_e.logic.exceptions;

public class InvalidRecipeException extends RuntimeException {

    /**
     * Error indicating an invalid Recipe
     *
     * @param e Additional error information
     */
    public InvalidRecipeException(String e) {
        super("Invalid recipe: " + e);
    }
}
