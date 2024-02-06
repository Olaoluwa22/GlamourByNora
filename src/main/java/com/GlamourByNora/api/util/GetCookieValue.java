package com.GlamourByNora.api.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class GetCookieValue {

    public String getCookieValue(HttpServletRequest request){
        Cookie[] cookie = request.getCookies();
        Cookie value = null;
        for (int i = 0; i < cookie.length; i++) {
            Cookie cookies = cookie[i];
            if (cookies.getName().equalsIgnoreCase("userEmail")) {
                value = cookies;
                break;
            }
        }
        return value.getValue();
    }
}
