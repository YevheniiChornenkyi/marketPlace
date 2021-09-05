package com.epam.yevheniy.chornenky.market.place.exceptions;

public class DBException extends CommonException{
    private static final String MESSAGE = "DataBase have some problem";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
