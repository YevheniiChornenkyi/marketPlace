package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.MD5Exception;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordService {
    private static final Logger LOGGER = LogManager.getLogger(PasswordService.class);

    private final String salt;

    public PasswordService(String salt) {
        this.salt = salt;
    }

    public String getHash(String psw) {
        try {
            String pswWithSalt = psw + salt;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(pswWithSalt.getBytes(StandardCharsets.UTF_8));
            byte[] digest = messageDigest.digest();

            StringBuilder hash = new StringBuilder();
            for (byte b : digest) {
                hash.append(Integer.toString((b & 0Xff) + 0X100, 16));
            }
            return hash.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Cant find md5 algorithm");
            throw new MD5Exception();
        }
    }
}
