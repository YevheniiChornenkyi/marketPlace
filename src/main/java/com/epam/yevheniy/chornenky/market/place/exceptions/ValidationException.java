package com.epam.yevheniy.chornenky.market.place.exceptions;

import java.util.Map;

/**
 * throws when the user tries to enter data that is incorrect for a particular field
 */
public class ValidationException extends CommonException{
    private final Map<String, String> validationMap;

    public ValidationException(Map<String, String> validationMap) {
        this.validationMap = validationMap;
    }

    public Map<String, String> getValidationMap() {
        return validationMap;
    }
}
