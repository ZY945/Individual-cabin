package com.cabin.controller;

import com.cabin.common.util.api.JenkinsUtil.JenkinsAPI;
import com.cabin.common.util.api.JenkinsUtil.empty.HomeInfo;
import com.cabin.common.util.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/5/29 15:12
 */
@RestController
@RequestMapping("/jenkins")
public class JenkinsController {
    //地址
    private static final String JenkinsIp = "http://120.55.99.46:";
    private static final String JenkinsPort = "8085";

    private static final String jenkinsUrl = JenkinsIp + JenkinsPort;
    private static final String userName = "user";
    private static final String token = "118b2f9feed9df54b21aeb0d25b6cd8401";


    @Autowired
    private JenkinsAPI jenkinsAPIl;

    @GetMapping("/home")
    public Result<HomeInfo> homeInfo() {
        //TODO 从用户设置中获取token
        HomeInfo homeInfo = jenkinsAPIl.getHomeInfo(jenkinsUrl, userName, token);
        return Result.success(homeInfo, "主页信息");
    }
}
