package com.epam.yevheniy.chornenky.market.place.exceptions;

public class EmailException extends CommonException{
    private static final String MESSAGE = "Invalid email, or message does not exist";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
