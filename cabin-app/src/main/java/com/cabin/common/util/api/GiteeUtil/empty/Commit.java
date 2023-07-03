package com.cabin.common.util.api.GiteeUtil.empty;

/**
 * @author 伍六七
 * @date 2023/5/17 9:49
 */
public class Commit {
    private String sha;
    private String url;


    @Override
    public String toString() {
        return "Commit{" +
                "sha='" + sha + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Commit() {
    }

    public Commit(String sha, String url) {
        this.sha = sha;
        this.url = url;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
