package com.cabin.utils.api.GiteeUtil.empty;

/**
 * @author 伍六七
 * @date 2023/5/16 16:01
 */

public class PathTree {
    private String path;
    private String type;
    private String mode;
    private String sha;
    private String size;
    private String url;

    @Override
    public String toString() {
        return "PathTree{" +
                "path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", mode='" + mode + '\'' +
                ", sha='" + sha + '\'' +
                ", size='" + size + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public PathTree() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

