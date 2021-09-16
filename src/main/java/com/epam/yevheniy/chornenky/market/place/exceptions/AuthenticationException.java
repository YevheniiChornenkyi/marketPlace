package com.epam.yevheniy.chornenky.market.place.exceptions;

public class AuthenticationException extends CommonException {
    private String MESSAGE = "Login or password incorrect";

    public AuthenticationException() {
    }

    public AuthenticationException(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
