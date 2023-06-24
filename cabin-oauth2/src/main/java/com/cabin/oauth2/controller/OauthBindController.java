package com.cabin.oauth2.controller;

import com.cabin.oauth2.common.enums.Oauth;
import com.cabin.oauth2.empty.bindAccount.BindAccount;
import com.cabin.oauth2.empty.bindAccount.BindAccountVo;
import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.service.OauthBindService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/feishu/account")
    public Result<BindAccountVo> bindFeiShuByAccount(@RequestBody BindAccount bindAccount) {
        String userName = bindAccount.getUserName();
        String passWord = bindAccount.getPassWord();
        Long id = bindAccount.getFeiShuId();
        //绑定

        BindAccountVo vo = oauthBindService.bindFeiShuByAccount(userName, passWord, id);
        Oauth oauth = vo.getOauth();
        //获取token
        if (oauth.equals(Oauth.OLDBIND)) {
            return Result.success(vo, "用户之前已绑定");
        } else if (oauth.equals(Oauth.NEWBIND)) {
            return Result.success(vo, "用户绑定成功");
        } else if (oauth.equals(Oauth.ISNOTUSER)) {
            return Result.fail("没有该用户");
        } else if (oauth.equals(Oauth.ISNOTFEISHUUSER)) {
            return Result.fail("没有该飞书用户信息");
        }
        return Result.fail("Oauth未获取到指定内容,未知错误");
    }

    @PostMapping("/feishu/email")
    public Result<BindAccountVo> bindFeiShuByEmail(@RequestBody BindAccount bindAccount) {
        String emailToken = bindAccount.getUserEmail();
        String code = bindAccount.getCode();
        Long id = bindAccount.getFeiShuId();
        //绑定

        BindAccountVo vo = oauthBindService.bindFeiShuByEmail(emailToken, code, id);
        Oauth oauth = vo.getOauth();
        //获取token
        if (oauth.equals(Oauth.OLDBIND)) {
            return Result.success(vo, "用户之前已绑定");
        } else if (oauth.equals(Oauth.NEWBIND)) {
            return Result.success(vo, "用户绑定成功");
        } else if (oauth.equals(Oauth.ISNOTUSER)) {
            return Result.fail("没有该用户");
        } else if (oauth.equals(Oauth.ISNOTFEISHUUSER)) {
            return Result.fail("没有该飞书用户信息");
        }
        return Result.fail("Oauth未获取到指定内容,未知错误");
    }

    @PostMapping("/github/account")
    public Result<BindAccountVo> bindGitHubByAccount(@RequestBody BindAccount bindAccount) {
        String userName = bindAccount.getUserName();
        String passWord = bindAccount.getPassWord();
        Long id = bindAccount.getGitHubId();
        //绑定

        BindAccountVo vo = oauthBindService.bindGitHubByAccount(userName, passWord, id);
        Oauth oauth = vo.getOauth();
        //获取token
        if (oauth.equals(Oauth.OLDBIND)) {
            return Result.success(vo, "用户之前已绑定");
        } else if (oauth.equals(Oauth.NEWBIND)) {
            return Result.success(vo, "用户绑定成功");
        } else if (oauth.equals(Oauth.ISNOTUSER)) {
            return Result.fail("没有该用户");
        } else if (oauth.equals(Oauth.ISNOTFEISHUUSER)) {
            return Result.fail("没有该飞书用户信息");
        }
        return Result.fail("Oauth未获取到指定内容,未知错误");
    }

    @PostMapping("/github/email")
    public Result<BindAccountVo> bindGitHubByEmail(@RequestBody BindAccount bindAccount) {
        String emailToken = bindAccount.getUserEmail();
        String code = bindAccount.getCode();
        Long id = bindAccount.getGitHubId();
        //绑定

        BindAccountVo vo = oauthBindService.bindGitHubByEmail(emailToken, code, id);
        Oauth oauth = vo.getOauth();
        //获取token
        if (oauth.equals(Oauth.OLDBIND)) {
            return Result.success(vo, "用户之前已绑定");
        } else if (oauth.equals(Oauth.NEWBIND)) {
            return Result.success(vo, "用户绑定成功");
        } else if (oauth.equals(Oauth.ISNOTUSER)) {
            return Result.fail("没有该用户");
        } else if (oauth.equals(Oauth.ISNOTFEISHUUSER)) {
            return Result.fail("没有该飞书用户信息");
        }
        return Result.fail("Oauth未获取到指定内容,未知错误");
    }

    @PostMapping("/gitee/account")
    public Result<BindAccountVo> bindGiteeByAccount(@RequestBody BindAccount bindAccount) {
        String userName = bindAccount.getUserName();
        String passWord = bindAccount.getPassWord();
        Long id = bindAccount.getGiteeId();
        //绑定

        BindAccountVo vo = oauthBindService.bindGiteeByAccount(userName, passWord, id);
        Oauth oauth = vo.getOauth();
        //获取token
        if (oauth.equals(Oauth.OLDBIND)) {
            return Result.success(vo, "用户之前已绑定");
        } else if (oauth.equals(Oauth.NEWBIND)) {
            return Result.success(vo, "用户绑定成功");
        } else if (oauth.equals(Oauth.ISNOTUSER)) {
            return Result.fail("没有该用户");
        } else if (oauth.equals(Oauth.ISNOTFEISHUUSER)) {
            return Result.fail("没有该飞书用户信息");
        }
        return Result.fail("Oauth未获取到指定内容,未知错误");
    }

    @PostMapping("/gitee/email")
    public Result<BindAccountVo> bindGiteeByEmail(@RequestBody BindAccount bindAccount) {
        String emailToken = bindAccount.getUserEmail();
        String code = bindAccount.getCode();
        Long id = bindAccount.getGiteeId();
        //绑定

        BindAccountVo vo = oauthBindService.bindGiteeByEmail(emailToken, code, id);
        Oauth oauth = vo.getOauth();
        //获取token
        if (oauth.equals(Oauth.OLDBIND)) {
            return Result.success(vo, "用户之前已绑定");
        } else if (oauth.equals(Oauth.NEWBIND)) {
            return Result.success(vo, "用户绑定成功");
        } else if (oauth.equals(Oauth.ISNOTUSER)) {
            return Result.fail("没有该用户");
        } else if (oauth.equals(Oauth.ISNOTFEISHUUSER)) {
            return Result.fail("没有该飞书用户信息");
        }
        return Result.fail("Oauth未获取到指定内容,未知错误");
    }
}
