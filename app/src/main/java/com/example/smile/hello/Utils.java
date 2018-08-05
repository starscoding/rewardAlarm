package com.example.smile.hello;


import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.cookie.Cookie;

public class Utils {

    private static List<Cookie> cookies;

    public static List<Cookie> getCookies() {
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    public static void setCookies(List<Cookie> cookies) {
        Utils.cookies = cookies;
    }
}


