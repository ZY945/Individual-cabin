package com.cabin.oauth2.controller;

import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.service.EmailLoginService;
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
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private EmailLoginService emailLoginService;

    @PostMapping("")
    public Result<String> logout(@RequestParam("token") String token) {
        Boolean success = null;
        success = emailLoginService.logout(token);
        return success ? Result.success("token已删除", "登出成功") : Result.fail("登出失败");
    }
}
