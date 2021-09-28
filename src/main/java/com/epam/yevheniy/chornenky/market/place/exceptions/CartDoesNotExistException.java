package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * throws when user try to confirm order without any purchases
 */
public class CartDoesNotExistException extends CommonException{
    private static final String MESSAGE = "Cart does not exist";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
