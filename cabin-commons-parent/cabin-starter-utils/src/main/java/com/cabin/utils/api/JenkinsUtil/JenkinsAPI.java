package com.cabin.utils.api.JenkinsUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import static com.cabin.utils.http.HttpUtil.getJsonObject;


/**
 * @author 伍六七
 * @date 2023/5/29 15:15
 */
public class JenkinsAPI {

    ///////////////////////////////URL///////////////////////////////
    /**
     * 获取 Jenkins 主页信息
     * GET /api/json
     */
    private static final String GetHomeInfoAPI = "/api/json";

    /**
     * 获取所有任务信息
     * Get api/json?tree=jobs[name,url,color]
     */
    private static final String GetAllTaskInfoAPI = "/api/json?tree=jobs[name,url,color]";

    ///
    /**
     * 获取指定任务的构建历史记录
     * Get job/{jobName}/api/json?tree=builds[number,url,result,duration,timestamp]
     */
    private static final String GetResultByTaskInfoAPI = "/job/%s/api/json?tree=builds[number,url,result,duration,timestamp]";

    /**
     * 通过任务名称获取指定任务的构建编号
     * Get job/{jobName}/api/json?tree=builds[number,url,result,duration,timestamp]
     */
    private static final String GetResultNameByTaskInfoAPI = "/job/%s/api/json?tree=builds[number]";
    ///
    /**
     * 根据任务名获取最后一次构建的结果
     * Get job/{jobName}/lastBuild/api/json?tree=result
     */
    private static final String GetResultByLastTaskInfoAPI = "/job/%s/lastBuild/api/json?tree=result";

    /**
     * 根据任务名和构建编号获取构建日志
     * Get job/{jobName}/{buildNumber}/consoleText
     */
    private static final String GetLogInfoAPI = "/job/%s/%s/consoleText";

    /**
     * 创建一个新任务
     * POST /createItem?name={jobName}&mode=copy&from={existingJobName}
     */
    private static final String CreateTaskInfoAPI = "/createItem?name=%s&mode=copy&from=%s";

    /**
     * 删除一个任务
     * POST /job/{jobName}/doDelete
     */
    private static final String DelTaskInfoAPI = "/job/%s/doDelete";


    ///////////////////////////////原API///////////////////////////////

    public static JSONObject getHomeInfo(String hostAndPort, String userName, String token) {
        String url = hostAndPort + GetHomeInfoAPI;
        JSONObject jsonObject = null;
        try {
            String auth = userName + ":" + token;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeader = "Basic " + encodedAuth;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", authHeader);
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            jsonObject = getJsonObject(jsonObject, con);
        } catch (MalformedURLException e) {
            throw new RuntimeException("url错误" + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static JSONObject getAllTaskInfo(String hostAndPort, String userName, String token) {
        String url = hostAndPort + GetAllTaskInfoAPI;
        JSONObject jsonObject = null;
        try {
            String auth = userName + ":" + token;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeader = "Basic " + encodedAuth;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", authHeader);
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            jsonObject = getJsonObject(jsonObject, con);
        } catch (MalformedURLException e) {
            throw new RuntimeException("url错误" + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static JSONObject getResultByTaskInfo(String hostAndPort, String userName, String token, String jobName) {
        String url = String.format(hostAndPort + GetResultByTaskInfoAPI, jobName);
        JSONObject jsonObject = null;
        try {
            String auth = userName + ":" + token;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeader = "Basic " + encodedAuth;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", authHeader);
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            jsonObject = getJsonObject(jsonObject, con);
        } catch (MalformedURLException e) {
            throw new RuntimeException("url错误" + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static JSONObject getResultNameByTaskInfoAPI(String hostAndPort, String userName, String token, String jobName) {
        String url = String.format(hostAndPort + GetResultNameByTaskInfoAPI, jobName);
        JSONObject jsonObject = null;
        try {
            String auth = userName + ":" + token;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeader = "Basic " + encodedAuth;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", authHeader);
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            jsonObject = getJsonObject(jsonObject, con);
        } catch (MalformedURLException e) {
            throw new RuntimeException("url错误" + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static JSONObject getResultByLastTaskInfo(String hostAndPort, String userName, String token, String jobName) {
        String url = String.format(hostAndPort + GetResultByLastTaskInfoAPI, jobName);
        JSONObject jsonObject = null;
        try {
            String auth = userName + ":" + token;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeader = "Basic " + encodedAuth;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", authHeader);
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            jsonObject = getJsonObject(jsonObject, con);
        } catch (MalformedURLException e) {
            throw new RuntimeException("url错误" + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static JSONObject getLogInfo(String hostAndPort, String userName, String token, String jobName, String buildNumber) {
        String url = String.format(hostAndPort + GetLogInfoAPI, jobName, buildNumber);
        JSONObject jsonObject = null;
        try {
            String auth = userName + ":" + token;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeader = "Basic " + encodedAuth;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", authHeader);
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            jsonObject = getJsonObject(jsonObject, con);
        } catch (MalformedURLException e) {
            throw new RuntimeException("url错误" + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    ///////////////////////////////从原api的json获取数据///////////////////////////////


}
