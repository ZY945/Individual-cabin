package com.cabin.oauth2.service.Impl;

import com.cabin.oauth2.empty.github.GitHubAccessToken;
import com.cabin.oauth2.empty.github.GitHubClient;
import com.cabin.oauth2.empty.github.GitHubUser;
import com.cabin.oauth2.empty.github.GitHubUserInfo;
import com.cabin.oauth2.repository.GitHubUserRepository;
import com.cabin.oauth2.service.GitHubService;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 伍六七
 * @date 2023/6/23 17:50
 */
@Service
@EnableConfigurationProperties(GitHubClient.class)
public class GitHubServiceImpl implements GitHubService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GitHubClient gitHubClient;
    @Autowired
    private GitHubUserRepository gitHubUserRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String getCodeUrl() {
        boolean isFirst = true;
        StringBuilder url = new StringBuilder(gitHubClient.getCodeUrlPrefix());
        Map<String, String> params = new HashMap<>();
        params.put("client_id", gitHubClient.getClientId());
        params.put("redirect_uri", gitHubClient.getRedirectUrlGetToken());
//        params.put("response_type", "code");
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
    public GitHubAccessToken getToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", gitHubClient.getClientId());
        params.add("client_secret", gitHubClient.getClientSecret());
        params.add("code", code);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ArrayList<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON);//设置接受类型为json,github传递是这种error=bad_verification_code&error_description=The+code+passed+is+incorrect+or+expired.&
        httpHeaders.setAccept(list);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<GitHubAccessToken> exchange = restTemplate.exchange(gitHubClient.getTokenUrlPrefix(), HttpMethod.POST, httpEntity, GitHubAccessToken.class);
        //用户token肯定不能泄露
        return exchange.getBody();
    }

    @Override
    public GitHubUser saveUser(GitHubAccessToken token) {
        String url = gitHubClient.getUserUrlPrefix();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccess_token());
        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<GitHubUserInfo> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, GitHubUserInfo.class);
        GitHubUserInfo body = exchange.getBody();
        if (body == null) {
            throw new RuntimeException("GitHub用户信息获取失败");
        }
        GitHubUser githubUser = new GitHubUser();
        BeanUtils.copyProperties(body, githubUser, GitHubUser.class);
        GitHubUser user = gitHubUserRepository.getGitHubUserByGitHubUserId(body.getGitHubUserId());
        if (user == null) {
            if (body.getSiteAdmin()) {
                githubUser.setSiteAdmin(1);
            }
            gitHubUserRepository.save(githubUser);
            return gitHubUserRepository.getGitHubUserByGitHubUserId(body.getGitHubUserId());
        }
        return user;
    }
}
