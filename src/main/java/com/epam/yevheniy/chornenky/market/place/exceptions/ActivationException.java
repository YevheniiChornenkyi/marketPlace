package com.epam.yevheniy.chornenky.market.place.exceptions;

public class ActivationException extends CommonException{
    private static final String MESSAGE = "User does not exist, or this key already used";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
