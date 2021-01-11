package com.wangjm.http.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wangjm
 * @date : 2021/1/8 09:16
 * RestTemplate测试
 * 需要先找一个适合发起请求的链接，这里是在本机上临时启动了一个
 */
//若测试时想要启动Spring，需要添加下面两个注解，并在pom中添加spring-context,spring-test依赖
//例如后面testAddHeadersInterceptor()需要用到Spring环境，需要开启下面两个注解
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:application.xml")
class RestTemplateUtilTest {
    private RestTemplate restTemplate;

    @BeforeEach
    public void init() {
        restTemplate = new RestTemplate();
    }

    @lombok.Data
    static class Greeting {
        private Long id;
        private String content;
    }

    @Test
    @Disabled
    public void testGet() {
        //直接加参数
        String url = "http://localhost:8081/greeting?content=wangjm";
        Greeting greeting = restTemplate.getForObject(url, Greeting.class);
        System.out.println(greeting);

        //字符串替换
        url = "http://localhost:8081/greeting?content={?}";
        greeting = restTemplate.getForObject(url, Greeting.class, "ole");
        System.out.println(greeting);

        //使用Map传参替换
        url = "http://localhost:8081/greeting?content={cont}";
        Map<String, String> params = new HashMap<String, String>(8);
        params.put("cont", "ole12138");
        greeting = restTemplate.getForObject(url, Greeting.class, params);
        System.out.println(greeting);

        // URI访问
        url = "http://localhost:8081/greeting?content=wangjm";
        URI uri = URI.create(url);
        greeting = restTemplate.getForObject(uri, Greeting.class);
        System.out.println(greeting);

        //******************************************************************


        //直接加参数
        url = "http://localhost:8081/greeting?content=wangjm";
        ResponseEntity<Greeting> res = restTemplate.getForEntity(url, Greeting.class);
        System.out.println(res);

        //字符串替换
        url = "http://localhost:8081/greeting?content={?}";
        res = restTemplate.getForEntity(url, Greeting.class, "ole");
        System.out.println(res);

        //使用Map传参替换
        url = "http://localhost:8081/greeting?content={cont}";
        params.clear();
        params.put("cont", "ole12138");
        res = restTemplate.getForEntity(url, Greeting.class, params);
        System.out.println(res);

        // URI访问
        url = "http://localhost:8081/greeting?content=wangjm";
        uri = URI.create(url);
        res = restTemplate.getForEntity(uri, Greeting.class);
        System.out.println(res);
    }

    @Test
    @Disabled
    public void testPost() {
        //post传入参数需要使用MultiValueMap
        String url = "http://localhost:8081/greeting";
        MultiValueMap params = new LinkedMultiValueMap();
        params.add("content", "Jimmy");
        Greeting greeting = restTemplate.postForObject(url, params, Greeting.class);
        System.out.println(greeting);

        //会发现使用普通map传参失败
        url = "http://localhost:8081/greeting";
        Map<String, Object> params2 = new HashMap<String, Object>(8);
        params2.put("content", "NAMEXXX");
        greeting = restTemplate.postForObject(url, params2, Greeting.class);
        System.out.println(greeting);

        // URI访问
        url = "http://localhost:8081/greeting";
        URI uri = URI.create(url);
        greeting = restTemplate.postForObject(uri, params, Greeting.class);
        System.out.println(greeting);

        /*//如果url中带点参数也是可以的(虽然post不建议）
        url = "http://localhost:8081/greeting?content=NAME";
        greeting = restTemplate.postForObject(url, null, Greeting.class);
        System.out.println(greeting);

        //如果url中带点参数也是可以的
        url = "http://localhost:8081/greeting?datax={?}&content={?}";
        greeting = restTemplate.postForObject(url, null, Greeting.class, "test", "NAME2");
        System.out.println(greeting);

        //如果url中带点参数也是可以的
        url = "http://localhost:8081/greeting?datax={name1}&content={name}";
        greeting = restTemplate.postForObject(url, null, Greeting.class, "NAME3", "datax");
        System.out.println(greeting);

        //如果url中带点参数也是可以的
        url = "http://localhost:8081/greeting?datax={data}&content={name}";
        Map<String, Object> map = new HashMap<String, Object>(8);
        map.put("data", "datadata");
        map.put("name", "NAME4");
        greeting = restTemplate.postForObject(url, null, Greeting.class, map);
        System.out.println(greeting);*/

        //*****************************************************************

        url = "http://localhost:8081/greeting";
        ResponseEntity<Greeting> res = restTemplate.postForEntity(url, params, Greeting.class);
        System.out.println(res);

        res = restTemplate.postForEntity(uri, params, Greeting.class);
        System.out.println(res);

        //*****************************************************************
        //postForLocation用于POST 数据到一个URL，返回新创建资源的URL
        //一般登录or注册都是post请求，而这些操作完成之后呢？大部分都是跳转到别的页面去了，
        // 这种场景下，就可以使用 `postForLocation` 了，提交数据，并获取返回的URI

        url = "http://localhost:8081/redirect";
        params.clear();
        uri = restTemplate.postForLocation(url, params);
        System.out.println(uri);
    }

    @Test
    public void testGetAddHeaders() {
        HttpHeaders headers = new HttpHeaders();
        //注意尽量不要用headers.add(),避免重复添加
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0" +
                        ".4103.97 Safari/537.36");
        headers.set("cookie", "my_user_id=haha123; UN=1231923;gr_user_id=welcome_yhh;");
        String url = "http://localhost:8081/get?name={name}&age={age}";
        //为了方便查看效果，请求该url返回的结果是：在body中写入请求中的params，headers，cookies，
        Map<String, Object> params = new HashMap<String, Object>(8);
        params.put("name", "user1");
        params.put("age", 23);
        HttpEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(null, headers),
                String.class, params);
        System.out.println(res);
    }

    @Test
    @Disabled
    public void testPostAddHeaders() {
        //为了方便查看效果，请求该url返回的结果是：在body中写入请求中的params，headers，cookies，
        String url = "http://localhost:8081/post";
        HttpHeaders headers = new HttpHeaders();
        //注意尽量不要用headers.add(),避免重复添加
        headers.set("user-agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0" +
                        ".4103.97 Safari/537.36");
        headers.set("cookie", "my_user_id=haha123; UN=1231923;gr_user_id=welcome_yhh;");
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        params.add("name", "user1");
        params.add("age", 23);
        //使用exchange携带headers
        HttpEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(params, headers),
                String.class);
        System.out.println(res);
        //使用postForXXX携带headers
        res = restTemplate.postForEntity(url, new HttpEntity<Map>(params, headers), String.class);
        System.out.println(res);
    }

    //这个测试需要在Spring环境下，需要开启测试类前的两个注解
    @Test
    public void testAddHeadersInterceptor() {
        // 借助拦截器的方式来实现塞统一的请求头
        ClientHttpRequestInterceptor interceptor = (httpRequest, bytes, execution) -> {
            httpRequest.getHeaders().set("user-agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83" +
                            ".0.4103.97 Safari/537.36");
            httpRequest.getHeaders().set("cookie", "my_user_id=haha123; UN=1231923;gr_user_id=interceptor;");
            return execution.execute(httpRequest, bytes);
        };

        restTemplate.getInterceptors().add(interceptor);

        String url = "http://localhost:8081/get?name=jimmy&age=22";
        String res = restTemplate.getForObject(url, String.class);
        System.out.println(res);
    }
}
