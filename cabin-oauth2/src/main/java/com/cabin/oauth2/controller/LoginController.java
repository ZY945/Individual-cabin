package com.cabin.oauth2.controller;

import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.service.AccountLoginService;
import com.cabin.oauth2.service.EmailLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/6/7 13:05
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private EmailLoginService emailLoginService;
    @Autowired
    private AccountLoginService accountLoginService;

    @GetMapping("/email/sendCode")
    public Result<String> sendCode(@RequestParam("userEmail") String userEmail) {
        String s = null;
        s = emailLoginService.sendCode(userEmail);
        return s == null ? Result.fail("发送验证码失败，请稍后重新发送") : Result.success("验证码在邮箱", "邮箱验证码发送成功");
    }

    @PostMapping("/email")
    public Result<String> login(@RequestParam("userEmail") String userEmail, @RequestParam("code") String code) {
        String token = emailLoginService.login(userEmail, code);
        return token == null ? Result.fail(code, "该验证码登录失败") : Result.success(token, "登录成功");
    }

    @GetMapping("/getToken")
    public Result<String> refreshToken(@RequestParam("userEmail") String userEmail, @RequestParam("token") String token) {
        String newToken = null;
        newToken = emailLoginService.refreshToken(userEmail, token);
        return newToken == null ? Result.authFail(token, "token错误") : Result.success(token, "token存在");
    }

//    @GetMapping("/login/email/new")
//    public Result<String> savePassWord( @RequestParam("token") String token, @RequestParam("passWord") String passWord) {
//        String s = null;
//        s = emailLoginService.savePassWord(token,passWord);
//        return s == null ? Result.fail("密码保存失败") : Result.success("即将跳转到首页","新用户密码保存成功");
//    }

    @PostMapping("/account")
    public Result<String> loginByAccount(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) {
        String token = accountLoginService.login(userName, passWord);
        return token == null ? Result.fail(userName, "该账户信息不对,登录失败") : Result.success(token, "登录成功");
    }

}
