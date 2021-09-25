package com.epam.yevheniy.chornenky.market.place.exceptions;

public class MD5Exception extends CommonException{
    private static final String MESSAGE= "Cant find md5 algorithm";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
