package com.cabin.oauth2.service;

import com.cabin.oauth2.common.enums.Oauth;

/**
 * @author 伍六七
 * @date 2023/6/9 14:25
 */
public interface OauthBindService {

    Oauth BindFeiShu(String userId, String openId);
}
