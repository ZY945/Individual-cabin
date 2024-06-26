package com.cabin.service.impl;

import com.cabin.common.util.api.JenkinsUtil.JenkinsAPI;
import com.cabin.utils.jsonUtil.JsonUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author 伍六七
 * @date 2023/5/29 15:58
 */
@SpringBootTest
public class JenkinsAPITest {
    @Autowired
    private JenkinsAPI jenkinsAPI;
    private static final String hostAndPort = "";
    private static final String user = "user";
    private static final String token = "";
    private static final String taskName = "test";

    @Test
    void getHomeInfo() {
        System.out.println(jenkinsAPI.getHomeInfo(hostAndPort, user, token));
    }

    @Test
    void getAllTaskInfo() {
        System.out.println(JenkinsAPI.getAllTaskInfo(hostAndPort, user, token));
    }

    @Test
    void getResultByTaskInfo() {
        System.out.println(JenkinsAPI.getResultByTaskInfo(hostAndPort, user, token, taskName));
    }

    @Test
    void getResultNameByTaskInfoAPI() {
        JSONObject name = JenkinsAPI.getResultNameByTaskInfoAPI(hostAndPort, user, token, taskName);
        String nameStr = null;
        try {
            nameStr = JsonUtil.removeClassField(name.get("builds").toString(), "_class");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(nameStr);
    }

    @Test
    void getResultByLastTaskInfo() {
        System.out.println(JenkinsAPI.getResultByLastTaskInfo(hostAndPort, user, token, taskName));
    }

    @Test
    void getLogInfo() {
        JSONObject name = JenkinsAPI.getResultNameByTaskInfoAPI(hostAndPort, user, token, taskName);
        System.out.println(JenkinsAPI.getLogInfo(hostAndPort, user, token, taskName, (String) name.toMap().get(0)));
    }
}
