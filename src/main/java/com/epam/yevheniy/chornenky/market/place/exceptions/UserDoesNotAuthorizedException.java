package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * throws when unauthorized user tries to access operations available only for authorized
 */
public class UserDoesNotAuthorizedException extends CommonException{
    private static final String MESSAGE = "User does not authorized";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
