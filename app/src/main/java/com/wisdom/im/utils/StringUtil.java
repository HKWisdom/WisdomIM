package com.wisdom.im.utils;

/**
 * Created by lidongzhi on 2017/3/29.
 */

public class StringUtil {

    private static final String USERNAME_REGEX = "^[A-Za-z]\\w{2,19}$";
    private static final String PASSWORD_REGEX = "^[0-9]{3,20}$";

    public static boolean isValidUsername(String username){
        return username.matches(USERNAME_REGEX);
    }

    public static boolean isValidPassword(String password){
        return password.matches(PASSWORD_REGEX);
    }
}
