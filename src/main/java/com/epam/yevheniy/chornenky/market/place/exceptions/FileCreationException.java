package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * throws when cannot create file or for ane reason
 */
public class FileCreationException extends CommonException{
    private static final String MESSAGE = "Cannot create file";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
