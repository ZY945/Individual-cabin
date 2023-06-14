package com.cabin.utils.api.GitLabUtil.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 伍六七 <br/>
 * @date 2023/5/31 11:28 <br/>
 * 支持gitlab版本(15.7.3) <br/>
 */
public class GitLabAPI {
    ///////////////////////////////原api///////////////////////////////
    //  https://docs.gitlab.com/ee/api/

    private static String GITLAB_HOST = "192.168.80.10";
    private static String GITLAB_PORT = "10000";
    private static String GITLAB_API_BASE_URL = "http://" + GITLAB_HOST + ":" + GITLAB_PORT + "/api/v4";

    public static void setGitLabHost(String gitlabHost) {
        GITLAB_HOST = gitlabHost;
    }

    public static void setGitlabPort(String gitlabPort) {
        GITLAB_PORT = gitlabPort;
    }

    /**
     * 获取项目信息(自带的项目1好像获取不到,报错500--gitlab 15.7.3)
     * GET /projects/:id
     *
     * @param token     访问令牌
     * @param projectId 项目 ID
     * @return 项目 JSON 对象
     */
    public static JSONObject getProject(String token, int projectId) {
        return getJSONObjectResp(apiUrl(String.format("/projects/%d", projectId)), token);
    }

    /**
     * 获取所有项目列表
     * GET /projects
     *
     * @param token 访问令牌
     * @return 项目列表 JSON 数组
     */
    public static JSONArray getProjectsList(String token) {
        return getAll(apiUrl("/projects"), token);
    }

    /**
     * 获取指定分支信息
     * GET /projects/:id/repository/branches/:branch
     *
     * @param token     访问令牌
     * @param projectId 项目 ID
     * @param branch    分支名
     * @return 分支 JSON 对象
     */
    public static JSONObject getBranch(String token, int projectId, String branch) {
        return getJSONObjectResp(apiUrl(String.format("/projects/%d/repository/branches/%s", projectId, branch)), token);
    }

    /**
     * 获取所有分支信息
     * GET /projects/:id/repository/branches
     *
     * @param token     访问令牌
     * @param projectId 项目 ID
     * @return 分支列表 JSON 数组
     */
    public static JSONArray getBranchList(String token, int projectId) {
        return getAll(apiUrl(String.format("/projects/%d/repository/branches", projectId)), token);
    }


    /**
     * 获取指定项目指定分支的文件树形结构
     * GET /projects/:id/repository/tree
     *
     * @param token     访问令牌
     * @param projectId 项目 ID
     * @param branch    分支名
     * @param recursive 是否递归查询子目录和文件(0 表示不递归，1 表示递归)
     * @param page      当前页数(从 1 开始)
     * @param per_page  每页显示条目数
     * @return 文件树形结构的 JSON 数据
     */
    public static JSONArray getFilesTree(String token, int projectId, String branch, int recursive, int page, int per_page) {
        String apiUrl = apiUrl(String.format("/projects/%d/repository/tree", projectId));
        Map<String, String> params = new HashMap<>();
        params.put("ref", branch);
        params.put("recursive", Integer.toString(recursive));
        params.put("page", Integer.toString(page));
        params.put("per_page", Integer.toString(per_page));
        return getAll(apiUrl, token, params);
    }


    /**
     * 获取指定文件内容       ----（Base64编码）待定
     * GET /projects/:id/repository/files/:file_path
     *
     * @param token     访问令牌
     * @param projectId 项目 ID
     * @param filePath  文件路径
     * @return 文件的 String 对象
     */
    public static String getFile(String token, int projectId, String filePath) {
        return getStringResp(apiUrl(String.format("/projects/%d/repository/files/%s/raw", projectId, filePath)), token);
    }

    /**
     * 获取所有提交信息
     * GET /projects/:id/repository/commits
     *
     * @param token     访问令牌
     * @param projectId 项目 ID
     * @return 提交列表 JSON 数组
     */
    public static JSONArray getAllCommits(String token, int projectId) {
        return getAll(apiUrl(String.format("/projects/%d/repository/commits", projectId)), token);
    }

    /**
     * 获取指定提交信息
     * GET /projects/:id/repository/commits/:sha
     *
     * @param token     访问令牌
     * @param projectId 项目 ID
     * @param sha       提交 SHA1 值
     * @return 提交 JSON 对象
     */
    public static JSONObject getCommit(String token, int projectId, String sha) {
        return getJSONObjectResp(apiUrl(String.format("/projects/%d/repository/commits/%s", projectId, sha)), token);
    }

    /**
     * 根据用户名查找用户信息
     * GET /users/:username
     *
     * @param token    访问令牌
     * @param username 用户名
     * @return 用户 JSON 对象
     */
    public static JSONObject getUserByUsername(String token, String username) {
        return getJSONObjectResp(apiUrl(String.format("/users/%s", username)), token);
    }

    /**
     * 获取当前用户信息
     * GET /user
     *
     * @param token 访问令牌
     * @return 用户 JSON 对象
     */
    public static JSONObject getCurrentUser(String token) {
        return getJSONObjectResp(apiUrl("/user"), token);
    }

    /**
     * 根据用户 ID 获取用户信息
     * GET /users/:id
     *
     * @param token  访问令牌
     * @param userId 用户 ID
     * @return 用户 JSON 对象
     */
    public static JSONObject getUserById(String token, int userId) {
        return getJSONObjectResp(apiUrl(String.format("/users/%d", userId)), token);
    }

    /**
     * 根据用户邮箱查找用户信息
     * GET /users?search=:email
     *
     * @param token 访问令牌
     * @param email 用户邮箱
     * @return 包含查询到的所有用户信息的 JSON 数组
     */
    public static JSONArray getUsersByEmail(String token, String email) {
        Map<String, String> params = new HashMap<>();
        params.put("search", email);
        return getAll(apiUrl("/users"), token, params);
    }

    /**
     * 发送 GET 请求并返回响应结果的 String 对象
     */
    private static String getStringResp(String urlStr, String token) {
        return sendRequest(urlStr, "GET", null, token);
    }

    /**
     * 发送 GET 请求并返回响应结果的 JSON 对象
     */
    private static JSONObject getJSONObjectResp(String urlStr, String token) {
        return new JSONObject(sendRequest(urlStr, "GET", null, token));
    }

    /**
     * 发送 GET 请求并返回响应结果的 JSON 对象
     */
    private static JSONArray getJSONArrayResp(String urlStr, String token) {
        return new JSONArray(sendRequest(urlStr, "GET", null, token));
    }

    /**
     * 发送 GET 请求并返回响应结果的 JSON 数组
     */
    private static JSONArray getAll(String urlStr, String token) {
        return getAll(urlStr, token, null);
    }

    /**
     * 发送 GET 请求并返回响应结果的 JSON 数组
     */
    private static JSONArray getAll(String urlStr, String token, Map<String, String> params) {
        JSONArray jsonArray = new JSONArray();

        int page = 1;
        boolean hasNextPage = true;
        while (hasNextPage) {
            // 构造请求 URL
            String urlWithParams = urlStr;
            if (params != null && !params.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String key : params.keySet()) {
                    sb.append("&");
                    sb.append(key);
                    sb.append("=");
                    sb.append(params.get(key));
                }
                urlWithParams += "?" + sb.substring(1);
                if (!params.containsKey("per_page") || !params.containsKey("page")) {
                    urlWithParams += "&per_page=100&page=" + page;
                }
            } else {
                urlWithParams += "?per_page=100&page=" + page;
            }

            // 发送请求并处理响应
            JSONArray array = getJSONArrayResp(urlWithParams, token);
            if (array != null) {
                jsonArray.putAll(array);
                hasNextPage = array.length() >= 100;
                page++;
            } else {
                hasNextPage = false;
            }
        }

        return jsonArray;
    }

    /**
     * 发送 HTTP 请求并获取响应结果
     */
    private static String sendRequest(String urlStr, String method, String requestBody, String token) {
        HttpURLConnection connection = null;
        try {
            // 创建连接对象，设置请求 URL 和请求方法
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json");
            if (token != null && !token.trim().isEmpty()) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }

            // 设置请求体（仅适用于 POST、PUT 和 DELETE 方法）
            if (requestBody != null && !requestBody.trim().isEmpty()) {
                connection.setDoOutput(true);
                connection.getOutputStream().write(requestBody.getBytes());
            }

            // 处理响应
            int responseCode = connection.getResponseCode();
            InputStream inputStream = null;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error when communicating with GitLab API.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * 组装 API URL
     */
    private static String apiUrl(String path) {
        return GITLAB_API_BASE_URL + path;
    }
}
