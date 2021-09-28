package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * throws when there is no such file on the server
 */
public class FileNotFoundException extends CommonException{
    private static final String MESSAGE ="File was not found on server";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
