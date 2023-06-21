package com.cabin.oauth2.service;

/**
 * @author 伍六七
 * @date 2023/6/21 9:06
 */
public interface AccountLoginService {

    String login(String userName, String passWord);

    String register(String userName, String email, String passWord);

    String getToken(String userEmail, String token);
}
