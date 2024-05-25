package com.example.momcare.util;

public class Validator {
    public static boolean isValidHexString(String str) {
        // Regular expression to match exactly 24 hexadecimal characters
        String regex = "^[0-9a-fA-F]{24}$";

        // Check if the string matches the regex pattern
        return str.matches(regex);
    }
}
