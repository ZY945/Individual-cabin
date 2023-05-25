package com.cabin.controller;

import com.cabin.common.response.Result;
import com.cabin.utils.GiteeUtil.GiteeAPI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/23 10:55
 */
@RestController
@RequestMapping("/gitee")
public class GiteeController {

    private static String token = "";

    /**
     * 只返回分支名
     *
     * @param owner 仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo  仓库路径(path)
     * @return 所有分支名
     */
    @GetMapping("/Branches")
    public Result<List<String>> getBranchesOnly(@RequestParam String owner, @RequestParam String repo) {
        //TODO 从用户设置中获取token
        List<String> branches = GiteeAPI.getBranchesOnly(token, owner, repo);
        return Result.success(branches, "所有分支");
    }

    /**
     * 获取该仓库下所有的路径(不区分文件还是文件夹)
     *
     * @param owner     仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo      仓库路径(path)
     * @param sha       可以是分支名(如master)、Commit或者目录Tree的SHA值
     * @param recursive 是否递归
     * @return 所有路径
     */
    @GetMapping("/allPath")
    public Result<List<String>> getPathList(@RequestParam String owner, @RequestParam String repo, @RequestParam String sha, @RequestParam int recursive) {
        //TODO 从用户设置中获取token 响应时间过长 5s
        List<String> pathList = GiteeAPI.getPathList(token, owner, repo, sha, recursive);
        return Result.success(pathList, repo + "仓库下所有的路径");
    }

    /**
     * 扫描节点为文件的路径
     *
     * @param owner     仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo      仓库路径(path)
     * @param sha       可以是分支名(如master)、Commit或者目录Tree的SHA值
     * @param recursive 是否递归
     * @return 节点为文件的路径
     */
    @GetMapping("/filePath")
    public Result<List<String>> getFilePathList(@RequestParam String owner, @RequestParam String repo, @RequestParam String sha, @RequestParam int recursive) {
        //TODO 从用户设置中获取token 响应时间过长 5s
        List<String> filePath = GiteeAPI.getFilePathList(token, owner, repo, sha, recursive);
        return Result.success(filePath, repo + "仓库下文件路径");
    }

    /**
     * 扫描特定文件后缀的路径
     *
     * @param owner     仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo      仓库路径(path)
     * @param sha       可以是分支名(如master)、Commit或者目录Tree的SHA值
     * @param recursive 是否递归
     * @param suffix    文件后缀(例如: java)不用加‘.’
     * @return 特定文件后缀的路径
     */
    @GetMapping("/suffixFilePath")
    public Result<List<String>> getFilePathListBySuffix(@RequestParam String owner, @RequestParam String repo, @RequestParam String sha, @RequestParam int recursive, @RequestParam String suffix) {
        //TODO 从用户设置中获取token
        List<String> filePath = GiteeAPI.getFilePathListBySuffix(token, owner, repo, sha, recursive, suffix);
        return Result.success(filePath, suffix + "后缀的文件");

    }

    /**
     * 获取指定路径的代码(已Base64解迷)
     *
     * @param owner 仓库所属空间地址(企业、组织或个人的地址path)
     * @param repo  仓库路径(path)
     * @param sha   可以是分支名(如master)、Commit或者目录Tree的SHA值
     * @param path  文件路径(例如:src/main/test.java)
     * @return String(代码块)
     */
    @GetMapping("/code")
    public Result<String> getCodeByPath(@RequestParam String owner, @RequestParam String repo, @RequestParam String sha, @RequestParam String path) {
        //TODO 从用户设置中获取token
        String code = GiteeAPI.getCodeByPath(token, owner, repo, sha, path);
        return Result.success(code, path + "的代码");
    }

}
