package com.cabin.oauth2.service;

import com.cabin.oauth2.empty.feishu.FeiShuAccessToken;
import com.cabin.oauth2.empty.feishu.FeiShuUser;

/**
 * @author 伍六七
 * @date 2023/6/6 17:02
 */
public interface FeiShuService {

    String getCodeUrl();

    FeiShuAccessToken getToken(String code);

    FeiShuUser saveUser(FeiShuAccessToken token);
}
