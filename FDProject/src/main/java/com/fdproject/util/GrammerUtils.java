package com.fdproject.util;

import org.springframework.stereotype.Controller;

@Controller
public class GrammerUtils {
    public static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
