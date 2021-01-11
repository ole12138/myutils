package com.wangjm.http;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author : wangjm
 * @date : 2021/1/9 00:30
 */
public class HttpPrintUtil {

    private String getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String name;

        JSONObject headers = new JSONObject();
        while (headerNames.hasMoreElements()) {
            name = headerNames.nextElement();
            headers.put(name, request.getHeader(name));
        }
        return headers.toJSONString();
    }

    private String getParams(HttpServletRequest request) {
        return JSONObject.toJSONString(request.getParameterMap());
    }

    private String getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "";
        }

        JSONObject ck = new JSONObject();
        for (Cookie cookie : cookies) {
            ck.put(cookie.getName(), cookie.getValue());
        }
        return ck.toJSONString();
    }

    private String buildResult(HttpServletRequest request) {
        return buildResult(request, null);
    }

    private String buildResult(HttpServletRequest request, Object obj) {
        String params = getParams(request);
        String headers = getHeaders(request);
        String cookies = getCookies(request);

        if (obj != null) {
            params += " | " + obj;
        }

        return "params: " + params + "\nheaders: " + headers + "\ncookies: " + cookies;
    }

}
