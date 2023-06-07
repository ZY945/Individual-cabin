package com.cabin.oauth2.controller;


import com.cabin.oauth2.empty.feishu.FeiShuAccessToken;
import com.cabin.oauth2.empty.feishu.FeiShuClient;
import com.cabin.oauth2.service.FeiShuService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author 伍六七
 * @date 2023/6/6 10:14
 */
@RestController()
@RequestMapping("/feishu")
@EnableConfigurationProperties(FeiShuClient.class)
public class FeiShuLoginController {


    @Autowired
    private FeiShuService feiShuService;

    /**
     * 不能是void
     * @return
     */
    @GetMapping("/code")
    public String toRequestWithCode(){
        return feiShuService.getCodeUrl();
    }


    @GetMapping("/access_token")
    public void getAccessToken(@RequestParam String code, HttpServletResponse response) throws IOException {
        FeiShuAccessToken token = feiShuService.getToken(code);
        //异步去保存用户信息
        feiShuService.saveUser(token);
        response.sendRedirect("http://127.0.0.1:8085/");
    }
}
