package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * throws when cannot delete file or for ane reason
 */
public class FileDeleteException extends CommonException{
    private final static String MESSAGE = "Cannot delete file";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
