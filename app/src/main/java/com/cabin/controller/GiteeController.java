package com.cabin.controller;

import com.cabin.common.response.Result;
import com.cabin.utils.API.GiteeUtil.GiteeAPI;
import com.cabin.utils.API.GiteeUtil.empty.GiteeBo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * <p>
     * owner 仓库所属空间地址(企业、组织或个人的地址path)
     * repo  仓库路径(path)
     *
     * @return 所有分支名
     */
    @GetMapping("/Branches")
    public Result<List<String>> getBranchesOnly(GiteeBo bo) {
        //TODO 从用户设置中获取token
        List<String> branches = GiteeAPI.getBranchesOnly(bo.getToken(), bo.getOwner(), bo.getRepo());
        return Result.success(branches, "所有分支");
    }

    /**
     * 获取该仓库下所有的路径(不区分文件还是文件夹)
     * <p>
     * owner     仓库所属空间地址(企业、组织或个人的地址path)
     * repo      仓库路径(path)
     * sha       可以是分支名(如master)、Commit或者目录Tree的SHA值
     * recursive 是否递归
     *
     * @return 所有路径
     */
    @GetMapping("/allPath")
    public Result<List<String>> getPathList(GiteeBo bo) {
        //TODO 从用户设置中获取token 响应时间过长 5s
        List<String> pathList = GiteeAPI.getPathList(bo.getToken(), bo.getOwner(), bo.getRepo(), bo.getSha(), bo.getRecursive());
        return Result.success(pathList, bo.getRepo() + "仓库下所有的路径");
    }

    /**
     * 扫描节点为文件的路径
     * <p>
     * owner     仓库所属空间地址(企业、组织或个人的地址path)
     * repo      仓库路径(path)
     * sha       可以是分支名(如master)、Commit或者目录Tree的SHA值
     * recursive 是否递归
     *
     * @return 节点为文件的路径
     */
    @GetMapping("/filePath")
    public Result<List<String>> getFilePathList(GiteeBo bo) {
        //TODO 从用户设置中获取token 响应时间过长 5s
        List<String> filePath = GiteeAPI.getFilePathList(bo.getToken(), bo.getOwner(), bo.getRepo(), bo.getSha(), bo.getRecursive());
        return Result.success(filePath, bo.getRepo() + "仓库下文件路径");
    }

    /**
     * 扫描特定文件后缀的路径
     * <p>
     * owner     仓库所属空间地址(企业、组织或个人的地址path)
     * repo      仓库路径(path)
     * sha       可以是分支名(如master)、Commit或者目录Tree的SHA值
     * recursive 是否递归
     * suffix    文件后缀(例如: java)不用加‘.’
     *
     * @return 特定文件后缀的路径
     */
    @GetMapping("/suffixFilePath")
    public Result<List<String>> getFilePathListBySuffix(GiteeBo bo) {
        //TODO 从用户设置中获取token
        List<String> filePath = GiteeAPI.getFilePathListBySuffix(bo.getToken(), bo.getOwner(), bo.getRepo(), bo.getSha(), bo.getRecursive(), bo.getSuffix());
        return Result.success(filePath, bo.getSuffix() + "后缀的文件");

    }

    /**
     * 获取指定路径的代码(已Base64解迷)
     * <p>
     * owner 仓库所属空间地址(企业、组织或个人的地址path)
     * repo  仓库路径(path)
     * sha   可以是分支名(如master)、Commit或者目录Tree的SHA值
     * path  文件路径(例如:src/main/test.java)
     *
     * @return String(代码块)
     */
    @GetMapping("/code")
    public Result<String> getCodeByPath(GiteeBo bo) {
        //TODO 从用户设置中获取token
        String code = GiteeAPI.getCodeByPath(bo.getToken(), bo.getOwner(), bo.getRepo(), bo.getSha(), bo.getPath());
        return Result.success(code, bo.getPath() + "的代码");
    }


    @GetMapping("/pull")
    public Result<String> downLoadByGit(GiteeBo bo) {
        String result = GiteeAPI.downLoadByGit(bo.getToken(), bo.getOwner(), bo.getRepo());
        return Result.success(result, "下载结束");
    }
}
