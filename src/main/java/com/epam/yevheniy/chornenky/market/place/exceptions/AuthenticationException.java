package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * throws when, when entering the account, the data entered does not match the data specified during registration.
 */
public class AuthenticationException extends CommonException {
    private String MESSAGE = "msg.incorrect-log-or-psw";

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
