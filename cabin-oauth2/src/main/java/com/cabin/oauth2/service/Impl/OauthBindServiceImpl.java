package com.cabin.oauth2.service.Impl;

import com.cabin.oauth2.common.enums.Oauth;
import com.cabin.oauth2.empty.OauthBind;
import com.cabin.oauth2.empty.User;
import com.cabin.oauth2.empty.bindAccount.BindAccountVo;
import com.cabin.oauth2.empty.feishu.FeiShuUserInfo;
import com.cabin.oauth2.repository.FeiShuUserRepository;
import com.cabin.oauth2.repository.OauthBindRepository;
import com.cabin.oauth2.repository.UserRepository;
import com.cabin.oauth2.service.EmailLoginService;
import com.cabin.oauth2.service.OauthBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 伍六七
 * @date 2023/6/9 14:25
 */
@Service
public class OauthBindServiceImpl implements OauthBindService {

    @Autowired
    private OauthBindRepository oauthRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeiShuUserRepository feiShuUserRepository;
    @Autowired
    private EmailLoginService emailLoginService;

    @Override
    public Oauth bindFeiShuByAccount(String userName, String passWord, Long feiShuUserId) {
        return null;
    }

    @Override
    public BindAccountVo bindFeiShuByEmail(String email, String code, Long feiShuUserId) {
        BindAccountVo vo = new BindAccountVo();
        String token = emailLoginService.login(email, code);
        vo.setToken(token);
        if (token == null) {
            vo.setOauth(Oauth.ISNOTUSER);
            return vo;
        }
        //根据email获取用户
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            vo.setOauth(Oauth.ISNOTUSER);
            return vo;
        }
        //根据userId获取飞书用户
        FeiShuUserInfo feiShuUser = feiShuUserRepository.getFeiShuUserInfoById(feiShuUserId);
        if (feiShuUser == null) {
            vo.setOauth(Oauth.ISNOTFEISHUUSER);
            return vo;
        }
        String openId = feiShuUser.getOpenId();
        OauthBind oauthBind = oauthRepository.getOauthByFeiShuOpenId(openId);
        if (oauthBind != null) {
            //已绑定
            vo.setOauth(Oauth.OLDBIND);
            return vo;
        }
        OauthBind newBind = new OauthBind();

        newBind.setUserId(user.getId());
        newBind.setFeiShuOpenId(openId);
        oauthRepository.save(newBind);
        vo.setOauth(Oauth.NEWBIND);
        return vo;
    }
}
