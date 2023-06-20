package com.cabin.oauth2.controller;

import com.cabin.oauth2.common.enums.Oauth;
import com.cabin.oauth2.empty.BindAccount;
import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.service.OauthBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/6/9 14:23
 */

@RestController
@RequestMapping("/bind")
public class OauthBindController {

    @Autowired
    private OauthBindService oauthBindService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/feishu")
    public Result<Oauth> bind(@RequestBody BindAccount bindAccount) {
        String emailToken = bindAccount.getEmailToken();
        String feiShuToken = bindAccount.getFeiShuToken();
        Oauth oauth = oauthBindService.BindFeiShu(emailToken, feiShuToken);
        if (oauth.equals(Oauth.OLDBIND)) {
            return Result.success(oauth, "用户之前已绑定");
        } else if (oauth.equals(Oauth.NEWBIND)) {
            return Result.success(oauth, "用户绑定成功");
        } else if (oauth.equals(Oauth.ISNOTUSER)) {
            return Result.fail("没有该用户");
        } else if (oauth.equals(Oauth.ISNOTFEISHUUSER)) {
            return Result.fail("没有该飞书用户信息");
        }
        return Result.fail("Oauth未获取到指定内容,未知错误");
    }
}
