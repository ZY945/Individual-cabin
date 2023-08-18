package com.cabin.oauth2.service;

/**
 * @author 伍六七
 * @date 2023/1/26 19:09
 */
public interface EmailLoginService {
    String sendCode(String userEmail);

    String login(String userEmail, String code);

    String savePassWord(String token, String passWord);

    String refreshToken(String userEmail, String token);

    Boolean logout(String token);

    String getAndSaveToken(String userEmail);

    String getAndSaveGuestToken(Long id);

}
