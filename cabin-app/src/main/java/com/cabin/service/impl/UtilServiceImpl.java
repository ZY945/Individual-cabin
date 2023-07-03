package com.cabin.service.impl;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.bloomfilter.BloomFilterUtil;
import com.cabin.entity.UrlMap;
import com.cabin.mapper.jpa.UrlMapRepository;
import com.cabin.service.UtilService;
import com.cabin.utils.commonUtil.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 伍六七
 * @date 2023/6/2 9:53
 */
@Service
public class UtilServiceImpl implements UtilService {


    @Value("${code.host}")
    private String host;

    @Autowired
    private UrlMapRepository urlMapRepository;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //最近使用的短链接缓存过期时间(分钟)
    private static final long TIMEOUT = 2;
    //创建布隆过滤器
    private static final BitMapBloomFilter FILTER = BloomFilterUtil.createBitMap(10);

    /**
     * 短链接
     *
     * @return
     */
    @Override
    public String getShortUrl(String shortUrl) {
        String key = "shortUrl:" + shortUrl;
        UrlMap urlMap = null;
        //想实现保存访问时间和访问次数,可以先存在redis,然后夜晚定时任务去跟新数据库,这样的话redis保存时间就需要长了
        //两种方法,保存urlMap在redis的value,每次访问都去更新访问次数和访问时间
        //第二种,单独开一个只保存次数和访问时间,
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            //先去缓存查找
//            String urlJson = redisTemplate.opsForValue().get(key);
//            try {
//                urlMap = JsonUtil.jsonToObject(urlJson, UrlMap.class);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }

//            Object o = redisTemplate.opsForValue().get(key);
//            urlMap = (UrlMap) o;
            ObjectMapper objectMapper = new ObjectMapper();
            urlMap = objectMapper.convertValue(redisTemplate.opsForValue().get(key), UrlMap.class);


        } else {
            urlMap = urlMapRepository.findUrlMapByShortUrl(shortUrl);
            urlMap.setClickCount(urlMap.getClickCount() + 1);
            urlMap.setLastVisitTime(new Date());
            urlMapRepository.save(urlMap);
            redisTemplate.opsForValue().set(key, urlMap, TIMEOUT, TimeUnit.HOURS);

        }
        if (urlMap != null) {
            return urlMap.getLongUrl();
        } else {
            return null;
        }
    }

    @Override
    public String setShortUrl(String longUrl) {
        UrlMap existed = urlMapRepository.queryUrlMapsByLongUrl(longUrl);
        if (existed != null) {
            String key = "shortUrl:" + existed.getShortUrl();
            //添加缓存
            redisTemplate.opsForValue().set(key, existed, TIMEOUT, TimeUnit.HOURS);
            return host + ":8080/util/" + existed.getShortUrl();
        }
        UrlMap urlMap = new UrlMap();
        urlMap.setLongUrl(longUrl);
        String random = StringUtil.shortUrl(longUrl);
        //如果重复了
        boolean shortUrlExi = urlMapRepository.existsUrlMapByShortUrl(random);
        String newLongUrl;
        while (shortUrlExi) {
            //加上jdk17生成的随机数
            newLongUrl = longUrl + StringUtil.getRandomInt();
            random = StringUtil.shortUrl(newLongUrl);
            shortUrlExi = urlMapRepository.existsUrlMapByShortUrl(random);
        }
        urlMap.setShortUrl(random);
        Date now = new Date();
        urlMap.setCreateTime(now);
        urlMap.setLastVisitTime(now);
        String key = "shortUrl:" + random;
        //保存到数据库
        UrlMap save = urlMapRepository.save(urlMap);
        //添加缓存
        redisTemplate.opsForValue().set(key, urlMap, TIMEOUT, TimeUnit.HOURS);
        random = host + ":8080/util/" + random;
        return random;
    }

}
