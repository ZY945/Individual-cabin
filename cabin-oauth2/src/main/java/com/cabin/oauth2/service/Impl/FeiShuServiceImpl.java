package com.cabin.oauth2.service.Impl;

import com.cabin.oauth2.empty.feishu.FeiShuAccessToken;
import com.cabin.oauth2.empty.feishu.FeiShuClient;
import com.cabin.oauth2.empty.feishu.FeiShuUser;
import com.cabin.oauth2.repository.FeiShuUserRepository;
import com.cabin.oauth2.service.FeiShuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
        //用户token肯定不能泄露
        return exchange.getBody();
    }

    @Override
    public FeiShuUser saveUser(FeiShuAccessToken token) {
        String url = feiShuClient.getUserUrlPrefix();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccess_token());
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<FeiShuUser> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, FeiShuUser.class);
        FeiShuUser body = exchange.getBody();
        if (body == null) {
            throw new RuntimeException("飞书用户信息获取失败");
        }
        FeiShuUser user = feiShuUserRepository.getFeiShuUserInfoByOpenId(body.getOpenId());
        if (user == null) {
            feiShuUserRepository.save(body);
            return feiShuUserRepository.getFeiShuUserInfoByOpenId(body.getOpenId());
        }
        return user;

    }
}
