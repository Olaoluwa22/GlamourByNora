package com.GlamourByNora.api.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
public class GetCookieValue {
    @Value("${app.cookie.login}")
    private String loginCookieName;
    public String getCookieValue(HttpServletRequest request){
        Cookie[] cookie = request.getCookies();
        Cookie value = null;
        for (Cookie cookies : cookie) {
            if (cookies.getName().equalsIgnoreCase("userEmail")) {
                value = cookies;
                break;
            }
        }
        return value.getValue();
    }
}
