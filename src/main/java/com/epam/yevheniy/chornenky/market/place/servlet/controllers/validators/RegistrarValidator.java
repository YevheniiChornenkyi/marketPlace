package com.epam.yevheniy.chornenky.market.place.servlet.controllers.validators;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarValidator {

    public static final String PSW_REGEX = "(?=.*[0-9])(?=.*[a-zа-яёії])[0-9a-zA-Zа-яА-ЯёЁіІїЇ!@#$%^&*]{6,}";
    public static final String LOGIN_REGEX = ".+@.+\\..+";

    private RegistrarValidator() {}

    /**
     * back-end login validation
     * login must be email
     * @param login login
     * @return boolean
     */
    public static boolean loginValidate(String login) {
        Pattern p = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = p.matcher(login);
        return matcher.matches();
    }

    /**
     * back-end login validation
     * psw must contains latin and numbers more than 6 characters
     * @param psw psw
     * @return boolean
     */
    public static boolean pswValidate(String psw) {
        Pattern p = Pattern.compile(PSW_REGEX);
        Matcher matcher = p.matcher(psw);
        return matcher.matches();
    }

    /**
     * back-end login validation
     * psw must contains latin and numbers more than 6 characters
     * throw ValidationException if not
     * @param psw psw
     */
    public static void pswValidateThrow(String psw) {
        if (!pswValidate(psw)) {
            throw new ValidationException(Map.of("psw", "msg.psw-format-false"));
        }
    }

    /**
     * equals two received in parameters string. Return result
     */
    public static boolean pswRepeatValidate(String psw, String pswRepeat) {
        return psw.equals(pswRepeat);
    }

}
