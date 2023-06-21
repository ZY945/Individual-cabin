package com.cabin.oauth2.controller;

import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.service.AccountLoginService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AccountLoginService accountLoginService;

    @PostMapping("/account")
    public Result<String> register(@RequestParam("userName") String userName,
                                   @RequestParam("email") String email,
                                   @RequestParam("passWord") String passWord) {
        String token = accountLoginService.register(userName, email, passWord);
        return token == null ? Result.fail(null, "该用户名已存在,注册失败") : Result.success(token, "登录成功");
    }
}
