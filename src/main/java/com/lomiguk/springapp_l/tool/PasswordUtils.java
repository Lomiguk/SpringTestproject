package com.lomiguk.springapp_l.tool;

public class PasswordUtils {
    public static String toHashString(String originalPassword) {
        if (originalPassword == null) {
            return null;
        }
        return String.valueOf(originalPassword.hashCode());
    }

}
