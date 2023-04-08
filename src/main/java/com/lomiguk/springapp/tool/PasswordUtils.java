package com.lomiguk.springapp.tool;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class PasswordUtils {
    public static String toHashString(String originalPassword) {
        if (originalPassword == null) {
            return null;
        }
        return String.valueOf(originalPassword.hashCode());
    }

    public static Long getHash(String origin){
        return Hashing.sha256()
                      .hashString(origin, StandardCharsets.UTF_8)
                      .padToLong();
    }
}
