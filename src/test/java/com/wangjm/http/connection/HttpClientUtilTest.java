package com.wangjm.http.connection;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : wangjm
 * @date : 2021/1/11 14:16
 */
class HttpClientUtilTest {

    @Test
    void doGetTest() {
        String url = "http://localhost:8081/get?name=NAME1";
        String res = HttpClientUtil.doGet(url);
        System.out.println(res);
    }

    @Test
    void doPostTest() {
        String url = "http://localhost:8081/post";
        Map<String, Object> params = new HashMap<>(8);
        params.put("name", "Name2");
        params.put("age", "32");
        String res = HttpClientUtil.doPost(url, params);
        System.out.println(res);
    }
}