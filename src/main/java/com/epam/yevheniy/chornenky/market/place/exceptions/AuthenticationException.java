package com.epam.yevheniy.chornenky.market.place.exceptions;

public class AuthenticationException extends CommonException {
    private static final String MESSAGE = "Login or password incorrect";

    public AuthenticationException() {
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
