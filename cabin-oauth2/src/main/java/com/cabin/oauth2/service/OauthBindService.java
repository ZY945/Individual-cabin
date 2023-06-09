package com.cabin.oauth2.service;

import com.cabin.oauth2.empty.bindAccount.BindAccountVo;

/**
 * @author 伍六七
 * @date 2023/6/9 14:25
 */
public interface OauthBindService {

    BindAccountVo bindFeiShuByAccount(String userName, String passWord, Long feiShuUserId);

    BindAccountVo bindFeiShuByEmail(String email, String code, Long feiShuUserId);

    BindAccountVo bindGitHubByAccount(String userName, String passWord, Long gitHubUserId);

    BindAccountVo bindGitHubByEmail(String email, String code, Long gitHubUserId);

    BindAccountVo bindGiteeByAccount(String userName, String passWord, Long giteeUserId);

    BindAccountVo bindGiteeByEmail(String email, String code, Long giteeUserId);


}
