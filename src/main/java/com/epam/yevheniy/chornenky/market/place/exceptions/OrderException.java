package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * throws when there are problems with orders
 */
public class OrderException extends CommonException{
    private final String MESSAGE;

    public OrderException(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    @Override
    public String getMessage() {
        return this.MESSAGE;
    }
}
