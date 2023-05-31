package com.cabin.controller;

import com.cabin.common.response.Result;
import com.cabin.utils.API.GitLabUtil.API.GitLabAPI;
import com.cabin.utils.API.GitLabUtil.empty.GitLabBo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * GitLab 控制器
 */
@RestController
@RequestMapping("/gitlab") // 添加映射路径
public class GitLabController {

    /**
     * 设置 GitLab 主机地址和端口号
     *
     * @param bo GitLab 相关信息
     * @return 返回设置成功或失败的结果
     */
    @GetMapping("/setting")
    public Result<GitLabBo> setToken(@RequestBody GitLabBo bo) {
        //TODO 应该有权限限制
        try {
            GitLabAPI.setGitLabHost(bo.getHost()); // 设置 GitLab 主机地址
            GitLabAPI.setGitlabPort(bo.getPort()); // 设置 GitLab 端口号
            return Result.success(bo, "设置成功");
        } catch (Exception e) {
            return Result.authFail(null, "错误");
        }
    }

    /**
     * 获取指定项目的信息
     *
     * @param bo GitLab 相关信息
     * @return 返回包含指定项目信息的 Map 对象
     */
    @GetMapping("/project")
    public Result<Map<String, Object>> getProject(@RequestBody GitLabBo bo) {
        //TODO token应该都统一保存在用户表设置
        try {
            JSONObject project = GitLabAPI.getProject(bo.getToken(), bo.getProjectId()); // 获取指定项目的信息
            Map<String, Object> projectMap = project.toMap(); // 转换为 Map 对象
            return Result.success(projectMap, "projectId为"+bo.getProjectId()+"的项目信息");
        } catch (Exception e) {
            return Result.authFail(null, "错误");
        }
    }

    /**
     * 获取所有项目列表的信息
     *
     * @param bo GitLab 相关信息
     * @return 返回包含所有项目信息的 List 对象
     */
    @GetMapping("/projects")
    public Result<List<Object>> getProjectsList(@RequestBody GitLabBo bo) {
        //TODO token应该都统一保存在用户表设置
        try {
            JSONArray projectsList = GitLabAPI.getProjectsList(bo.getToken()); // 获取所有项目列表的信息
            return Result.success(projectsList.toList(), "所有项目列表的信息");
        } catch (Exception e) {
            return Result.authFail(null, "错误");
        }
    }

    /**
     * 获取指定项目特定分支的信息
     *
     * @param bo GitLab 相关信息
     * @return 返回包含指定分支信息的 Map 对象
     */
    @GetMapping("/branch")
    public Result<Map<String, Object>> getBranch(@RequestBody GitLabBo bo) {
        //TODO token应该都统一保存在用户表设置
        try {
            JSONObject projectsList = GitLabAPI.getBranch(bo.getToken(), bo.getProjectId(), bo.getBranch()); // 获取指定项目特定分支的信息
            Map<String, Object> paojectMap = projectsList.toMap(); // 转换为 Map 对象
            return Result.success(paojectMap, "projectId为"+bo.getProjectId()+"项目"+bo.getBranch()+"分支的信息");
        } catch (Exception e) {
            return Result.authFail(null, "错误");
        }
    }

    /**
     * 获取指定项目所有分支的信息
     *
     * @param bo GitLab 相关信息
     * @return 返回包含所有分支信息的 List 对象
     */
    @GetMapping("/branchs")
    public Result<List<Object>> getBranchList(@RequestBody GitLabBo bo) {
        //TODO token应该都统一保存在用户表设置
        try {
            JSONArray branchJson = GitLabAPI.getBranchList(bo.getToken(), bo.getProjectId()); // 获取指定项目所有分支的信息
            return Result.success(branchJson.toList(), "projectId为"+bo.getProjectId()+"项目所有分支的信息");
        } catch (Exception e) {
            return Result.authFail(null, "错误");
        }
    }
}
