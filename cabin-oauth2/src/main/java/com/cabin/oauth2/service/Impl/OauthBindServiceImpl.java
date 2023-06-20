package com.cabin.oauth2.service.Impl;

import com.cabin.oauth2.common.enums.Oauth;
import com.cabin.oauth2.empty.OauthBind;
import com.cabin.oauth2.empty.User;
import com.cabin.oauth2.empty.feishu.FeiShuUserInfo;
import com.cabin.oauth2.repository.FeiShuUserRepository;
import com.cabin.oauth2.repository.OauthBindRepository;
import com.cabin.oauth2.repository.UserRepository;
import com.cabin.oauth2.service.OauthBindService;
import com.cabin.utils.commonUtil.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * @author 伍六七
 * @date 2023/6/9 14:25
 */
@Service
public class OauthBindServiceImpl implements OauthBindService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private OauthBindRepository oauthRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeiShuUserRepository feiShuUserRepository;

    @Override
    public Oauth BindFeiShu(String emailToken, String feiShuToken) {
        String email = redisTemplate.opsForValue().get("email:token:" + emailToken);
        String openId = redisTemplate.opsForValue().get("feishu:token:" + feiShuToken);
        if (email == null) {
            return Oauth.ISNOTUSER;
        }
        if (openId == null) {
            return Oauth.ISNOTFEISHUUSER;
        }
        //解密
        try {
            email = Base64Util.decoderGetStrByStr(email);
            openId = Base64Util.decoderGetStrByStr(openId);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //根据email获取用户
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            return Oauth.ISNOTUSER;
        }
        //根据openID获取飞书用户
        FeiShuUserInfo feiShuUser = feiShuUserRepository.getFeiShuUserInfoByOpenId(openId);
        if (feiShuUser == null) {
            return Oauth.ISNOTFEISHUUSER;
        }
        OauthBind oauthBind = oauthRepository.getOauthByFeiShuOpenId(openId);
        if (oauthBind != null) {
            //已绑定
            return Oauth.OLDBIND;
        }
        OauthBind newBind = new OauthBind();

        newBind.setUserId(user.getId());
        newBind.setFeiShuOpenId(openId);
        oauthRepository.save(newBind);
        return Oauth.NEWBIND;
    }
}
