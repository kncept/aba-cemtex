package com.kncept.abacemtex.utils;

public class StringUtils {

    public static String padding(String padding, int length) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++)
            sb.append(padding);
        return sb.toString();
    }

    public static String blankString(int length) {
        return padding(" ", length);
    }

}
