package com.epam.yevheniy.chornenky.market.place.exceptions;

public class UserDoesNotAuthorizedException extends CommonException{
    private static final String MESSAGE = "User does not authorized";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
