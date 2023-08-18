package com.cabin.service.impl;

import com.cabin.common.util.api.GiteeUtil.GiteeAPI;
import com.cabin.file.fileUtil.FileUtil;
import com.cabin.file.fileUtil.empty.FileAbsolutePath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 伍六七
 * @date 2023/5/17 14:00
 */
@SpringBootTest
public class GiteeAPITest {
    @Autowired
    private GiteeAPI giteeAPI;
    private static String token = "";
    private static String owner = "";
    private static String repo = "";
    private static String sha = "";
    private static String suffix = "";
    private static String path = "";

    @Test
    void getBranchesOnly() {
        System.out.println("获取仓库分支名称");
        System.out.println(giteeAPI.getBranchesOnly(token, owner, repo));
    }

    @Test
    void getPathList() {
        System.out.println("获取所有路径");
        System.out.println(giteeAPI.getPathList(token, owner, repo, sha, 1));
    }

    @Test
    void getFilePathList() {
        System.out.println("获取文件+路径");
        System.out.println(giteeAPI.getFilePathList(token, owner, repo, sha, 1));
    }

    @Test
    void getFilePathListBySuffix() {
        System.out.println("获取指定后缀的文件+路径");
        System.out.println(giteeAPI.getFilePathListBySuffix(token, owner, repo, sha, 1, suffix));
    }

    @Test
    void getCodeByPath() {
        System.out.println("获取指定文件的内容");
        System.out.println(giteeAPI.getCodeByPath(token, owner, repo, sha, path));
    }

    @Test
    void downLoadByGit() {
        System.out.println("下载仓库(zip格式)");
        System.out.println(giteeAPI.downLoadByGit(token, owner, repo));
    }

    /////////文件API////////////
    @Test
    void getFileByAbsolutePath1() {
        FileAbsolutePath fileAbsolutePath = FileUtil.scanFiles("F:\\study\\CodeX\\CodeX\\CodeX-user\\src\\main\\java");
        System.out.println(fileAbsolutePath);
    }

    @Test
    void getFileByAbsolutePathAndSuffix() {
        FileAbsolutePath fileAbsolutePath = FileUtil.scanFiles("F:\\study\\CodeX\\CodeX\\CodeX-user\\src\\main\\java", "java");
        System.out.println(fileAbsolutePath);
    }

}
