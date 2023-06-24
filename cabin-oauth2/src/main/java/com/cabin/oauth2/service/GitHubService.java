package com.cabin.oauth2.service;

import com.cabin.oauth2.empty.github.GitHubAccessToken;
import com.cabin.oauth2.empty.github.GitHubUser;

/**
 * @author 伍六七
 * @date 2023/6/23 17:50
 */
public interface GitHubService {
    String getCodeUrl();

    GitHubAccessToken getToken(String code);

    GitHubUser saveUser(GitHubAccessToken token);
}
