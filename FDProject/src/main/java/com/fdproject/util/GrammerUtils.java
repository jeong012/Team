package com.fdproject.util;


import org.thymeleaf.util.StringUtils;

import java.util.List;

public class GrammerUtils {
    public static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String str(List<String> list) {
        String result = "";
        return result = StringUtils.join(list, ",");
    }
}
