package com.kit.promokhapi.helpers;

public class StringHelper {
    public static String toCamelCase(String str) {
        if (str == null || str.length() == 0)
            return "";

    String[] parts = str.split("_");
    String ret = new String();

        for (int i = 0; i < parts.length; i++) {
        String part = parts[i];

        if (i == 0)
            ret += part.substring(0, 1).toLowerCase();
        else/* w ww.j  a va  2 s. c om*/
            ret += part.substring(0, 1).toUpperCase();

        ret += part.substring(1).toLowerCase();
    }

        return ret;
}
}
