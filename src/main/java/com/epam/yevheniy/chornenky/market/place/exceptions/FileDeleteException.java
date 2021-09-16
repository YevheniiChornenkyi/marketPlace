package com.epam.yevheniy.chornenky.market.place.exceptions;

public class FileDeleteException extends CommonException{
    private final static String MESSAGE = "Cannot delete file";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
