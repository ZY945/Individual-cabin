package com.cabin.oauth2.controller;

import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.service.AccountLoginService;
import com.cabin.oauth2.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/6/21 10:59
 */
@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private AccountLoginService accountLoginService;

    @PostMapping("/account")
    public Result<String> register(@RequestParam("userName") String userName,
                                   @RequestParam("email") String email,
                                   @RequestParam("code") String code,
                                   @RequestParam("passWord") String passWord) {
        String token = accountLoginService.register(userName, email, code, passWord);
        return token == null ? Result.fail(null, "该用户名已存在,注册失败") : Result.success(token, "登录成功");
    }

    @GetMapping("/email/sendCode")
    public Result<String> sendCode(@RequestParam("userEmail") String userEmail) {
        String s = null;
        s = registerService.sendCode(userEmail);
        return s == null ? Result.fail("发送验证码失败，请稍后重新发送") : Result.success("验证码在邮箱", "邮箱验证码发送成功");
    }
}
