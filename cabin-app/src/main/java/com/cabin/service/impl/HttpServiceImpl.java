package com.cabin.service.impl;

import com.cabin.service.HttpService;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 伍六七
 * @date 2023/8/17 17:34
 */
public class HttpServiceImpl implements HttpService {
    @Override
    public ClassicHttpResponse getResponse(String url) {
        return null;
    }

    @Override
    public String getResponseContent(String url) {
        // get 请求
        AtomicReference<String> atomicReference = new AtomicReference<>();
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get(url)
                    .build();
            httpclient.execute(httpGet, response -> {
                int code = response.getCode(); //200
                String reasonPhrase = response.getReasonPhrase(); //OK
                final HttpEntity entity = response.getEntity();
                // 获取响应信息的JSON--是String格式的
                atomicReference.set(EntityUtils.toString(entity));
                // 结束连接
                EntityUtils.consume(entity);
                return response;
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return atomicReference.get();
    }

//    public static void main(String[] args) {
//        // post请求
//
//        ClassicHttpRequest httpPost = ClassicRequestBuilder.post("http://httpbin.org/post")
//                .setEntity(new UrlEncodedFormEntity(Arrays.asList(
//                        new BasicNameValuePair("username", "vip"),
//                        new BasicNameValuePair("password", "secret"))))
//                .build();
//        httpclient.execute(httpPost, response -> {
//            System.out.println(response.getCode() + " " + response.getReasonPhrase());
//            final HttpEntity entity2 = response.getEntity();
//            // do something useful with the response body
//            EntityUtils.consume(entity2);
//            return null;
//        });
//    }
}
