package com.cabin.oauth2.service.Impl;

import com.cabin.oauth2.empty.gitee.GiteeAccessToken;
import com.cabin.oauth2.empty.gitee.GiteeClient;
import com.cabin.oauth2.empty.gitee.GiteeUser;
import com.cabin.oauth2.empty.gitee.GiteeUserInfo;
import com.cabin.oauth2.repository.GiteeUserRepository;
import com.cabin.oauth2.service.GiteeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 伍六七
 * @date 2023/6/24 23:09
 */
@Service
@EnableConfigurationProperties(GiteeClient.class)
public class GiteeServiceImpl implements GiteeService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GiteeClient giteeClient;
    @Autowired
    private GiteeUserRepository giteeUserRepository;

    @Override
    public String getCodeUrl() {
        boolean isFirst = true;
        StringBuilder url = new StringBuilder(giteeClient.getCodeUrlPrefix());
        Map<String, String> params = new HashMap<>();
        params.put("client_id", giteeClient.getClientId());
        params.put("redirect_uri", giteeClient.getRedirectUrlGetToken());
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
    public GiteeAccessToken getToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", giteeClient.getClientId());
        params.add("client_secret", giteeClient.getClientSecret());
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", giteeClient.getRedirectUrlGetToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ArrayList<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON);//设置接受类型为json,github传递是这种error=bad_verification_code&error_description=The+code+passed+is+incorrect+or+expired.&
        httpHeaders.setAccept(list);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<GiteeAccessToken> exchange = restTemplate.exchange(giteeClient.getTokenUrlPrefix(), HttpMethod.POST, httpEntity, GiteeAccessToken.class);
        //用户token肯定不能泄露
        return exchange.getBody();
    }

    @Override
    public GiteeUser saveUser(GiteeAccessToken token) {
        String url = giteeClient.getUserUrlPrefix();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccess_token());
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<GiteeUserInfo> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, GiteeUserInfo.class);
        GiteeUserInfo body = exchange.getBody();
        if (body == null) {
            throw new RuntimeException("Gitee用户信息获取失败");
        }
        GiteeUser giteeUser = new GiteeUser();
        BeanUtils.copyProperties(body, giteeUser, GiteeUser.class);
        GiteeUser user = giteeUserRepository.getGiteeUserByGiteeUserId(body.getGiteeUserId());
        if (user == null) {
            giteeUserRepository.save(giteeUser);
            return giteeUserRepository.getGiteeUserByGiteeUserId(body.getGiteeUserId());
        }
        return user;
    }
}
