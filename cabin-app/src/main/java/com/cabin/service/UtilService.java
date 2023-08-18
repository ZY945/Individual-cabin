package com.cabin.service;

import com.cabin.entity.UrlMap;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/2 9:52
 */
public interface UtilService {

    String getShortUrl(String shortUrl);

    String setShortUrl(String longUrl);

    List<UrlMap> listShortUrl();


}
