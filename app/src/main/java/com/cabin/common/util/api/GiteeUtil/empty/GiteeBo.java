package com.cabin.common.util.api.GiteeUtil.empty;

/**
 * @author 伍六七
 * @date 2023/5/29 18:41
 */

public class GiteeBo {
    private String token;
    private String owner;
    private String repo;
    private String sha;
    private int recursive;
    private String suffix;
    private String path;
    private String per_page;
    private String page;

    public GiteeBo() {
    }

    public GiteeBo(String token, String owner, String repo, String sha, int recursive, String suffix, String path, String per_page, String page) {
        this.token = token;
        this.owner = owner;
        this.repo = repo;
        this.sha = sha;
        this.recursive = recursive;
        this.suffix = suffix;
        this.path = path;
        this.per_page = per_page;
        this.page = page;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
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

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
