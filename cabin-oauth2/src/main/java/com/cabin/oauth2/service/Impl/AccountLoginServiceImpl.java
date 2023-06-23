package com.cabin.oauth2.service.Impl;

import com.cabin.oauth2.empty.User;
import com.cabin.oauth2.repository.UserRepository;
import com.cabin.oauth2.service.AccountLoginService;
import com.cabin.oauth2.service.EmailLoginService;
import com.cabin.utils.encipherUtil.nonreversible.NonReversible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 伍六七
 * @date 2023/6/21 9:06
 * 统一拿email加密成jwt,redis存用户id
 */
@Service
public class AccountLoginServiceImpl implements AccountLoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailLoginService emailLoginService;

    @Override
    public String login(String userName, String passWord) {
        String token = null;
        boolean existsed = userRepository.existsUserByUserName(userName);
        if (!existsed) {
            return token;
        }
        //加密密码
        String passWordEncode = encryptedPassword(passWord);
        User user = userRepository.getUserByUserNameAndPassWord(userName, passWordEncode);
        if (user != null && user.getId() != null) {
            token = emailLoginService.getAndSaveToken(user.getEmail());
        }
        return token;
    }

    @Override
    public String register(String userName, String email, String code, String passWord) {
        String token = null;
        boolean existsed = userRepository.existsUserByEmail(email);
        if (existsed) {
            return token;
        }
        String redisCode = null;
        redisCode = redisTemplate.opsForValue().get("email:Code:" + email);
        if (!code.equals(redisCode)) {
            return token;
        }
        redisTemplate.delete("email:Code:" + email);
        //加密密码 目前使用SHA256
        User user = new User();
        user.setEmail(email);
        user.setUserName(userName);
        String passWordEncode = "{SHA256}" + NonReversible.encryptBySHA256(passWord);
        user.setPassWord(passWordEncode);
        user.setCreatTime(new Date());
        user.setLastLoginTime(new Date());
        userRepository.save(user);
        token = emailLoginService.getAndSaveToken(user.getEmail());
        return token;
    }

    @Override
    public String getToken(String userEmail, String token) {
        return null;
    }

    @Override
    public String encryptedPassword(String passWord) {
        return "{SHA256}" + NonReversible.encryptBySHA256(passWord);
    }
}
