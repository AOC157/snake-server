package com.company.utils;

public class NumberUtils {
    public static final String INTEGER_VALID_CHARACTERS = "-0123456789";
    public static final String DOUBLE_VALID_CHARACTERS = "-0123456789.";

    public static boolean isValidIntegerCharacter(char ch){
        return INTEGER_VALID_CHARACTERS.contains("" + ch);
    }

    public static boolean isValidDoubleChar(char ch){
        return DOUBLE_VALID_CHARACTERS.contains("" + ch);
    }
}
