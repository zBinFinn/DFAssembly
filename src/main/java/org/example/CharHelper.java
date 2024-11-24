package org.example;

public class CharHelper {
    public static boolean alphabetic(char c) {
        return Character.isAlphabetic(c);
    }

    public static boolean uppercase(char c) {
        return Character.isUpperCase(c);
    }

    public static boolean whitespace(char c) {
        return Character.isWhitespace(c);
    }

    public static boolean numeric(char c) {
        return Character.isDigit(c);
    }


}
