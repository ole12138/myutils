package com.wangjm.http.connection;

import org.junit.jupiter.api.Test;

/**
 * @author : wangjm
 * @date : 2021/1/10 15:53
 */
class HttpURLConnectionUtilTest {

    @Test
    void doGetTest() {
        String url = "http://localhost:8081/get?name=NAME1";
        String res = HttpURLConnectionUtil.doGet(url);
        System.out.println(res);
    }

    @Test
    void doPostTest() {
        String url = "http://localhost:8081/post";
        String params = "name=NAME2&age=23";
        String res = HttpURLConnectionUtil.doPost(url,params);
        System.out.println(res);
    }
}