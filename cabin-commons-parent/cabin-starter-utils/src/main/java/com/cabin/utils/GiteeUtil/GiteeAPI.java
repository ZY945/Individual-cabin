package com.cabin.utils.GiteeUtil;


import com.cabin.utils.GiteeUtil.empty.Branch;
import com.cabin.utils.GiteeUtil.empty.PathTree;
import com.cabin.utils.fileUtil.FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/17 10:10
 */
public class GiteeAPI {


    ///////////////////////////////原api///////////////////////////////
    //  https://gitee.com/api/v5/swagger#/getV5ReposOwnerRepoStargazers?ex=no

    /**
     * 原api-获取目录
     * https://gitee.com/api/v5/repos/{owner}/{repo}/git/trees/{sha}?access_token={access_token}&recursive={recursive}<br/>
     *
     * @param sha       分支
     * @param recursive 是否递归
     * @return [{"mode":"",
     * "path":"",
     * "size":0,
     * "type":"",
     * "sha":"",
     * "url":""},{}]
     */
    private static JSONArray getPathTreeAPI(String token, String owner, String repo, String sha, int recursive) {
        //终止:如果是包含后缀，表示是文件
        String url = String.format("https://gitee.com/api/v5/repos/%s/%s/git/trees/%s?access_token=" + token,
                owner,
                repo,
                sha);
        JSONArray jsonArray = null;
        if (recursive == 1) {
            url = url + "&recursive=1";
        }

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                InputStream inStream = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 解析 JSON 数据
                jsonArray = new JSONObject(response.toString()).getJSONArray("tree");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }

    /**
     * 原api-获取分支
     */
    private static JSONArray getBranchesAPI(String token, String owner, String repo) {
        //终止:如果是包含后缀，表示是文件
        String url = String.format("https://gitee.com/api/v5/repos/%s/%s/branches?access_token=" + token,
                owner,
                repo);
        JSONArray jsonArray = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                InputStream inStream = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 解析 JSON 数据
                jsonArray = new JSONArray(response.toString());

//                System.out.println(response.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonArray;
    }


    /**
     * 原api-获取指定文件内容(Base64编码)() <br/>
     * 获取文件内容：https://gitee.com/api/v5/repos/{owner}/{repo}/contents/{path}（HTTP GET 请求）<br/>
     * 更新文件内容：https://gitee.com/api/v5/repos/{owner}/{repo}/contents/{path}（HTTP PUT 请求）<br/>
     */
    private static JSONObject getCodeAPI(String token, String owner, String repo, String sha, String path) {
        //终止:如果是包含后缀，表示是文件
        String url = String.format("https://gitee.com/api/v5/repos/%s/%s/contents/%s?ref=%s&access_token=" + token,
                owner,
                repo,
                path,
                sha);
        JSONObject jsonObject = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            // 发送 HTTP 请求
            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                InputStream inStream = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                jsonObject = new JSONObject(response.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
    ///////////////////////////////封装///////////////////////////////

    /**
     * 原api进行封装
     *
     * @param token 授权码
     * @param owner 仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo  仓库路径(path)
     * @return 分支信息的封装
     */
    private static List<Branch> getBranchesList(String token, String owner, String repo) {
        JSONArray jsonArray = getBranchesAPI(token, owner, repo);
        List<Branch> branches = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        branches = objectMapper.convertValue(jsonArray.toList(), new TypeReference<List<Branch>>() {
        });
        return branches;
    }


    /**
     * 只返回分支名
     *
     * @param token 授权码
     * @param owner 仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo  仓库路径(path)
     * @return 所有分支名
     */
    public static List<String> getBranchesOnly(String token, String owner, String repo) {
        JSONArray jsonArray = getBranchesAPI(token, owner, repo);
//        List<String> branchNameList = new ArrayList<>();
        List<Branch> branches = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        branches = objectMapper.convertValue(jsonArray.toList(), new TypeReference<List<Branch>>() {
        });
        return branches.stream().map(Branch::getName).toList();
    }


    /**
     * 获取该仓库下所有的路径(不区分文件还是文件夹)
     *
     * @param token     授权码
     * @param owner     仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo      仓库路径(path)
     * @param sha       可以是分支名(如master)、Commit或者目录Tree的SHA值
     * @param recursive 是否递归
     * @return 所有路径
     */
    public static List<String> getPathList(String token, String owner, String repo, String sha, int recursive) {
        JSONArray jsonArray = getPathTreeAPI(token, owner, repo, sha, recursive);
        List<PathTree> pathTrees = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        pathTrees = objectMapper.convertValue(jsonArray.toList(), new TypeReference<List<PathTree>>() {
        });
        return pathTrees.stream().map(PathTree::getPath).toList();
    }

    /**
     * 扫描节点为文件的路径
     *
     * @param token     授权码
     * @param owner     仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo      仓库路径(path)
     * @param sha       可以是分支名(如master)、Commit或者目录Tree的SHA值
     * @param recursive 是否递归
     * @return 节点为文件的路径
     */
    public static List<String> getFilePathList(String token, String owner, String repo, String sha, int recursive) {
        JSONArray jsonArray = getPathTreeAPI(token, owner, repo, sha, recursive);
        List<String> list = new ArrayList<>();

        List<PathTree> pathTrees = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        pathTrees = objectMapper.convertValue(jsonArray.toList(), new TypeReference<List<PathTree>>() {
        });
        pathTrees.forEach(l -> {
            if (FileUtil.isFileBySuffix(l.getPath(), "")) {
                list.add(l.getPath());
            }
        });
        return list;
    }

    /**
     * 扫描特定文件后缀的路径
     *
     * @param token     授权码
     * @param owner     仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo      仓库路径(path)
     * @param sha       可以是分支名(如master)、Commit或者目录Tree的SHA值
     * @param recursive 是否递归
     * @param suffix    文件后缀(例如: java)不用加‘.’
     * @return 特定文件后缀的路径
     */
    public static List<String> getFilePathListBySuffix(String token, String owner, String repo, String sha, int recursive, String suffix) {
        JSONArray jsonArray = getPathTreeAPI(token, owner, repo, sha, recursive);
        List<String> list = new ArrayList<>();
        List<PathTree> pathTrees = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        pathTrees = objectMapper.convertValue(jsonArray.toList(), new TypeReference<List<PathTree>>() {
        });
        pathTrees.forEach(l -> {
            if (FileUtil.isFileBySuffix(l.getPath(), suffix)) {
                list.add(l.getPath());
            }
        });
        return list;
    }

    /**
     * 获取指定路径的代码(已Base64解迷)
     *
     * @param token 授权码
     * @param owner 仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo  仓库路径(path)
     * @param path  文件路径(例如:src/main/test.java)
     * @return String(代码块)
     */
    public static String getCodeByPath(String token, String owner, String repo, String sha, String path) {
        JSONObject jsonObject = getCodeAPI(token, owner, repo, sha, path);
        String content = jsonObject.getString("content");
        Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(content);
        return new String(decode);
    }
}
