package com.epam.yevheniy.chornenky.market.place.servlet.controllers.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarValidator {

    public static final String PSW_REGEX = "(?=.*[0-9])(?=.*[a-zа-яёії])[0-9a-zA-Zа-яА-ЯёЁіІїЇ!@#$%^&*]{6,}";
    public static final String LOGIN_REGEX = ".+@.+\\..+";

    private RegistrarValidator() {}

    public static boolean loginValidate(String login) {
        Pattern p = Pattern.compile(LOGIN_REGEX);
        Matcher matcher = p.matcher(login);
        return matcher.matches();
    }

    public static boolean pswValidate(String psw) {
        Pattern p = Pattern.compile(PSW_REGEX);
        Matcher matcher = p.matcher(psw);
        return matcher.matches();
    }

    public static boolean pswRepeatValidate(String psw, String pswRepeat) {
        return psw.equals(pswRepeat);
    }

}
