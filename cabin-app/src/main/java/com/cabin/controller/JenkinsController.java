package com.cabin.controller;

import com.cabin.common.util.api.JenkinsUtil.JenkinsAPI;
import com.cabin.common.util.api.JenkinsUtil.empty.HomeInfo;
import com.cabin.utils.response.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/5/29 15:12
 */
@RestController
@RequestMapping("/jenkins")
public class JenkinsController {
    //地址
    private static final String JenkinsIp = ":";
    private static final String JenkinsPort = "";

    private static final String jenkinsUrl = JenkinsIp + JenkinsPort;
    private static final String userName = "";
    private static final String token = "";


    @Autowired
    private JenkinsAPI jenkinsAPIl;

    @GetMapping("/home")
    public Result<HomeInfo> getHomeInfo() {
        //TODO 从用户设置中获取token
        HomeInfo homeInfo = jenkinsAPIl.getHomeInfo(jenkinsUrl, userName, token);
        return Result.success(homeInfo, "主页信息");
    }

    /**
     * 获取构建日志
     *
     * @param projectName
     * @return
     */
    @GetMapping("/log/last")
    public Result<String> getLastBuild(@RequestParam(value = "projectName") String projectName) {
        //TODO 从用户设置中获取token
        String lastBuildLog = jenkinsAPIl.getLastLog(jenkinsUrl, userName, token, projectName);
        return Result.success(lastBuildLog, "构建日志");
    }

    /**
     * 根据项目名进行构建
     *
     * @param projectName
     * @return
     */
    @GetMapping("/build")
    public Result<JSONObject> buildProject(@RequestParam(value = "projectName") String projectName) {
        //TODO 从用户设置中获取token
        JSONObject jsonObject = JenkinsAPI.buildProject(jenkinsUrl, userName, token, projectName);
        return Result.success(jsonObject, "主页信息");
    }

}
