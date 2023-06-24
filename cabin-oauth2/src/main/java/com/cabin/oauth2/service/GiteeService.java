package com.cabin.oauth2.service;

import com.cabin.oauth2.empty.gitee.GiteeAccessToken;
import com.cabin.oauth2.empty.gitee.GiteeUser;

/**
 * @author 伍六七
 * @date 2023/6/24 23:09
 */
public interface GiteeService {
    String getCodeUrl();

    GiteeAccessToken getToken(String code);

    GiteeUser saveUser(GiteeAccessToken token);
}
