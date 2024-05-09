package com.GlamourByNora.api.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
public class GetCookieValue {
    @Value("${app.cookie.login}")
    private String loginCookieName;
    public String getCookieValue(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cookie value = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("userEmail")) {
                value = cookie;
                break;
            }
        }
        return value.getValue();
    }
}