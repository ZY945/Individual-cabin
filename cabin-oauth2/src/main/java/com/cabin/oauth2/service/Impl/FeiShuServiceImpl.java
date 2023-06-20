package com.cabin.oauth2.service.Impl;

import com.cabin.oauth2.empty.feishu.FeiShuAccessToken;
import com.cabin.oauth2.empty.feishu.FeiShuClient;
import com.cabin.oauth2.empty.feishu.FeiShuUserInfo;
import com.cabin.oauth2.repository.FeiShuUserRepository;
import com.cabin.oauth2.service.FeiShuService;
import com.cabin.utils.commonUtil.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 伍六七
 * @date 2023/6/6 17:03
 */
@Service
@EnableConfigurationProperties(FeiShuClient.class)
public class FeiShuServiceImpl implements FeiShuService {

    @Autowired
    private FeiShuClient feiShuClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FeiShuUserRepository feiShuUserRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String getCodeUrl() {
        boolean isFirst = true;
        StringBuilder url = new StringBuilder(feiShuClient.getCodeUrlPrefix());
        Map<String, String> params = new HashMap<>();
        params.put("client_id", feiShuClient.getClientId());
        params.put("redirect_uri", feiShuClient.getRedirectUrlGetToken());
        params.put("response_type", "code");
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (isFirst) {
                url.append("?");
                isFirst = false;
            } else {
                url.append("&");
            }
            url.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
            url.append('=');
            url.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));
        }
        return url.toString();
    }

    @Override
    public FeiShuAccessToken getToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", feiShuClient.getClientId());
        params.add("client_secret", feiShuClient.getClientSecret());
        params.add("code", code);
        params.add("redirect_uri", feiShuClient.getRedirectUrlGetToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<FeiShuAccessToken> exchange = restTemplate.exchange(feiShuClient.getTokenUrlPrefix(), HttpMethod.POST, httpEntity, FeiShuAccessToken.class);
        FeiShuAccessToken body = exchange.getBody();
        //用户token肯定不能泄露
        return body;
    }

    @Override
    public boolean saveUser(FeiShuAccessToken token) {
        String url = feiShuClient.getUserUrlPrefix();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccess_token());
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<FeiShuUserInfo> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, FeiShuUserInfo.class);
        FeiShuUserInfo body = exchange.getBody();
        if (body == null) {
            throw new RuntimeException("飞书用户信息获取失败");
        }
        //将openId存入redis
        String openIdEncoder;
        try {
            openIdEncoder = Base64Util.encoderGetStrByStr(body.getOpenId());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("openId加密失败" + e);
        }
        if (openIdEncoder == null) {
            throw new RuntimeException("openId未加密成功");
        }
        //该用户已存在
        FeiShuUserInfo exit = feiShuUserRepository.getFeiShuUserInfoByOpenId(body.getOpenId());
        if (exit == null) {
            System.out.println("该用户首次登录,保存信息");
            FeiShuUserInfo save = feiShuUserRepository.save(body);
            redisTemplate.opsForValue().set("feishu:token:" + save.getId(), openIdEncoder, 5, TimeUnit.MINUTES);
            return true;
        }
        redisTemplate.opsForValue().set("feishu:token:" + exit.getId(), openIdEncoder, 5, TimeUnit.MINUTES);
        System.out.println("该用户已存在,直接登录");
        return false;
    }
}
