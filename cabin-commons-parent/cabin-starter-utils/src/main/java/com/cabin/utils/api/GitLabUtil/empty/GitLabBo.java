package com.cabin.utils.api.GitLabUtil.empty;

/**
 * @author 伍六七
 * @date 2023/5/31 13:23
 */
public class GitLabBo {
    private String host;
    private String port;
    private String token;
    private int projectId;
    private String branch;
    private String sha;
    private int recursive;
    private String filePath;
    private int page;
    private int per_page;

    public GitLabBo() {
    }

    public GitLabBo(String host, String port, String token, int projectId, String branch, String sha, int recursive, String filePath, int page, int per_page) {
        this.host = host;
        this.port = port;
        this.token = token;
        this.projectId = projectId;
        this.branch = branch;
        this.sha = sha;
        this.recursive = recursive;
        this.filePath = filePath;
        this.page = page;
        this.per_page = per_page;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public int getRecursive() {
        return recursive;
    }

    public void setRecursive(int recursive) {
        this.recursive = recursive;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }
}
