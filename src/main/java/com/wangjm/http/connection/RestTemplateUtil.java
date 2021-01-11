package com.wangjm.http.connection;

import org.springframework.web.client.RestTemplate;

/**
 * @author : wangjm
 * @date : 2021/1/7 22:36
 * 使用Spring框架下的RestTemplate可以发起Http请求，该类已经封装得比较好用了，这里只是罗列。
 * 并没有进一步封装，直接使用RestTemplate即可。
 * 注意：RestTemplate需要spring-web的maven依赖。
 * 本类的对应测试类中有使用示例
 */
public class RestTemplateUtil {
    //可以看到RestTemplate源码中的函数，分别对应get,post,head,put,patch,delete,options几种Http访问方式，
    //只是多了个exchange函数
}
