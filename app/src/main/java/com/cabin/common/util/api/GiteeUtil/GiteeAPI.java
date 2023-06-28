package com.cabin.common.util.api.GiteeUtil;


import com.cabin.common.util.api.GiteeUtil.empty.Branch;
import com.cabin.common.util.api.GiteeUtil.empty.PathTree;
import com.cabin.utils.commonUtil.Base64Util;
import com.cabin.utils.fileUtil.FileUtil;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/17 10:10
 */
@Component
public class GiteeAPI {


    ///////////////////////////////原api///////////////////////////////
    //  https://gitee.com/api/v5/swagger#/getV5ReposOwnerRepoStargazers?ex=no

    /**
     * 原api-获取目录
     * https://gitee.com/api/v5/repos/{owner}/{repo}/git/trees/{sha}?access_token={access_token}&recursive={recursive}&page={page}&per_page={per_page}<br/>
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
        if (jsonArray == null) {
            throw new RuntimeException("没有获取到数据");
        }
        return JacksonUtils.convertValue(jsonArray, Branch.class);
    }


    /**
     * 只返回分支名
     *
     * @param token 授权码
     * @param owner 仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo  仓库路径(path)
     * @return 所有分支名
     */
    public List<String> getBranchesOnly(String token, String owner, String repo) {
        JSONArray jsonArray = getBranchesAPI(token, owner, repo);
        if (jsonArray == null) {
            throw new RuntimeException("没有获取到数据");
        }
        List<Branch> branches = JacksonUtils.convertValue(jsonArray, Branch.class);
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
    public List<String> getPathList(String token, String owner, String repo, String sha, int recursive) {
        JSONArray jsonArray = getPathTreeAPI(token, owner, repo, sha, recursive);
        if (jsonArray == null) {
            throw new RuntimeException("没有获取到数据");
        }
        List<PathTree> pathTrees = JacksonUtils.convertValue(jsonArray, PathTree.class);
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
    public List<String> getFilePathList(String token, String owner, String repo, String sha, int recursive) {
        JSONArray jsonArray = getPathTreeAPI(token, owner, repo, sha, recursive);
        if (jsonArray == null) {
            throw new RuntimeException("没有获取到数据");
        }
        List<String> list = new ArrayList<>();

        List<PathTree> pathTrees = JacksonUtils.convertValue(jsonArray, PathTree.class);
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
    public List<String> getFilePathListBySuffix(String token, String owner, String repo, String sha, int recursive, String suffix) {
        JSONArray jsonArray = getPathTreeAPI(token, owner, repo, sha, recursive);
        List<String> list = new ArrayList<>();
        if (jsonArray == null) {
            throw new RuntimeException("没有获取到数据");
        }
        List<PathTree> pathTrees = JacksonUtils.convertValue(jsonArray, PathTree.class);
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
    public String getCodeByPath(String token, String owner, String repo, String sha, String path) {
        JSONObject jsonObject = getCodeAPI(token, owner, repo, sha, path);
        if (jsonObject == null) {
            throw new RuntimeException("没有获取到数据");
        }
        String content = jsonObject.getString("content");
        return Base64Util.decoderGetStrByStr(content);
    }

    public String downLoadByGit(String token, String owner, String repo) {
        String downloadUrl = String.format("https://gitee.com/%s/%s/repository/archive/master.zip?access_token=" + token,
                owner,
                repo);

        System.out.println(downloadUrl);
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("F:\\CODE_mid\\MyCode\\Individual-cabin\\gitZip\\" + repo + ".zip"));
            int length;
            byte[] bytes = new byte[2048];
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            //TODO 返回文件路径
            return "文件路径";
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("文件IO错误" + e);
        }
    }
}
