package com.epam.yevheniy.chornenky.market.place.exceptions;

public class FileCreationException extends CommonException{
    private static final String MESSAGE = "Cannot create file";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
