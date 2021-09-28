package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * throws when trying to send a letter without the final address or the letter itself
 */
public class EmailException extends CommonException{
    private static final String MESSAGE = "Invalid email, or message does not exist";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
