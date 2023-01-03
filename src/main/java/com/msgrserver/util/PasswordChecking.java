package com.msgrserver.util;

import java.util.regex.Pattern;

public class PasswordChecking {
    /*
    Must have at least one numeric character
    Must have at least one lowercase character
    Must have at least one uppercase character
    Must have at least one special symbol among @#$%
    Password length should be between 8 and 20
     */
    private final static String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";

    public static boolean isValidPassword(String password) {
        return Pattern.compile(regex).matcher(password).matches();
    }
}