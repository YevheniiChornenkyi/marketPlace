package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * throws when a non-activated user logs into an account.
 */
public class ActivationException extends CommonException{
    private static final String MESSAGE = "User does not exist, or this key already used";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
