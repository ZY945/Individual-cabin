package com.cabin.oauth2.controller;


import com.cabin.oauth2.empty.feishu.FeiShuAccessToken;
import com.cabin.oauth2.empty.feishu.FeiShuClient;
import com.cabin.oauth2.empty.response.Result;
import com.cabin.oauth2.service.FeiShuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<String> getAccessToken(@RequestParam String code){
        FeiShuAccessToken token = feiShuService.getToken(code);
        //异步去保存用户信息,然后绑定是一个单独的接口
        boolean exit = feiShuService.saveUser(token);
        return exit ?Result.success("success","该用户首次登录,保存信息"):Result.success("success","该用户已存在,直接登录");
        //前端去重定向
//        response.sendRedirect("http://127.0.0.1:8085/");
    }
}
