package com.day.l.video.config;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class UserConfig {
    private static String userName;
    private static String password;
    private static String token;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserConfig.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserConfig.password = password;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserConfig.token = token;
    }
}
