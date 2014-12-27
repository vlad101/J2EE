package com.vladimir.util;

import javax.servlet.http.Cookie;

/**
 * A utility class that gets the value of a cookie.
 * 
 * @author vladimir
 */
public class CookieUtil {
    
    /**
     * Get the value of the cookie.
     * @param cookies
     * @param cookieName
     * @return the String value of the cookie
     * 
     * Code that uses the CookieUtil class to get the value of a cookie.
     * Cookie[] cookies = request.getCookies();
     * String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");
     */
    public static String getCookieValue(Cookie[] cookies, String cookieName) {
        String cookieValue = "";
        if(cookies != null) {
                for(Cookie cookie : cookies) {
                    if(cookieName.equals(cookie.getName())) {
                        cookieValue = cookie.getValue();
                    }
                }
        }
        return cookieValue;
    }
}