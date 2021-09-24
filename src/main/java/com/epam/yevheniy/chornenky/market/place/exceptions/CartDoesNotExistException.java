package com.epam.yevheniy.chornenky.market.place.exceptions;

public class CartDoesNotExistException extends CommonException{
    private static final String MESSAGE = "Cart does not exist";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
