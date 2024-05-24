package com.cabin.service;

import org.apache.hc.core5.http.ClassicHttpResponse;

/**
 * @author 伍六七
 * @date 2023/8/17 17:34
 */
public interface HttpService {
    ClassicHttpResponse getResponse(String url);

    String getResponseContent(String url);
}
