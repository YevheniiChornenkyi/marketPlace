package com.epam.yevheniy.chornenky.market.place.servlet.controllers.validators;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsValidator {

    private static final List<String> EXTENSIONS_ALLOWED = List.of("png", "jpeg", "jpg");
    public static final String IS_NUMBER_REGEX = "^\\d+(?:[.,]\\d{1,2})?$";

    public static void validatePrice(String price){
        if (!price.matches(IS_NUMBER_REGEX)) {
            throw new ValidationException(Collections.singletonMap("price", "Incorrect data, enter the cost of the product."));
        }
    }

    public static void validatePriceImage(String price, String fileName) {
        Map<String, String> validationMap = new HashMap<>();
        if (!price.matches(IS_NUMBER_REGEX)) {
            validationMap.put("price", "Incorrect data, enter the cost of the product.");
        }
        if (!isPicture(fileName)) {
            validationMap.put("image", "Incorrect image extension, select picture with next extensions: png, jpeg, jpg");
        }
        if (!validationMap.isEmpty()) {
            throw new ValidationException(validationMap);
        }
    }

    private static boolean isPicture(String fileName) {
        String[] split = fileName.split("\\.");
        if (split.length > 0) {
            String extension = split[split.length - 1].toLowerCase();
            return EXTENSIONS_ALLOWED.contains(extension);
        }
        return false;
    }
}
