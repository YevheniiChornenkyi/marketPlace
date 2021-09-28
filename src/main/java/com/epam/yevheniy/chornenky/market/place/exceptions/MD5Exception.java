package com.epam.yevheniy.chornenky.market.place.exceptions;

/**
 * personal md5 exception.
 * for hash passwords service
 */
public class MD5Exception extends CommonException{
    private static final String MESSAGE= "Cant find md5 algorithm";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
