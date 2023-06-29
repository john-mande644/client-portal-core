/**
 * Validation.java
 * collection of various methods for data validation
 */

package com.owd.core;

/**
 * Liheng Qiao July 17, 2002
 */

public class Validation {
    public static boolean isAlphanumeric(String s) {
        /**
         * loosely check if a String contains only alphanumeric,
         * evaluate s to see if it contains any ascii code less than 32 (Space)
         * or greater than 122('z').
         */

        char[] content = s.toCharArray();
        for (int i = 0; i < content.length; i++) {
            if (content[i] < ' ' || content[i] > 'z')
                return false;
        }
        return true;
    }

    public static boolean isCharacterString(String s) {
        /**
         * loosely check if a String is a pure character string,
         * evaluate s to see if it contains only legal English letters
         *
         */

        char[] content = s.toCharArray();
        for (int i = 0; i < content.length; i++) {
            if (content[i] < 'A' && content[i] != ' ')
                return false;
            else if (content[i] > 'Z' && content[i] < 'a')
                return false;
            else if (content[i] > 'z')
                return false;
        }
        return true;

    }

    public static boolean is_int(String s) {
        /**
         * loosely check if a String is an integer,
         * evaluate s to see if it contains any ascii code less than 48 (0)
         * or greater than 57(9).
         */

        char[] content = s.toCharArray();
        for (int i = 0; i < content.length; i++) {
            if (content[i] < '0' || content[i] > '9')
                return false;
        }
        return true;
    }

    public static boolean is_float(String s) {
        /**
         * loosely check if a String is a valid decimal number,
         * evaluate s to see if it contains any ascii code less than 48 (0)
         * or greater than 57(9) except 46('.').
         */

        char[] content = s.toCharArray();
        for (int i = 0; i < content.length; i++) {
            if (content[i] < '0' && content[i] != '.')
                return false;
            else if (content[i] > '9')
                return false;
        }
        return true;
    }

}
