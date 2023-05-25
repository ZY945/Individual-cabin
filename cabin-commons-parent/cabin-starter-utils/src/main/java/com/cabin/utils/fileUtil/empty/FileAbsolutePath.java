package com.cabin.utils.fileUtil.empty;

import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/18 17:32
 */
public class FileAbsolutePath {
    private String basePath;
    private List<String> usingPath;
    private List<String> ignorePath;
    private Long usingTotal;
    private Long total;

    public FileAbsolutePath() {
    }

    public FileAbsolutePath(String basePath, List<String> usingPath, List<String> ignorePath, Long usingTotal, Long total) {
        this.basePath = basePath;
        this.usingPath = usingPath;
        this.ignorePath = ignorePath;
        this.usingTotal = usingTotal;
        this.total = total;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public List<String> getUsingPath() {
        return usingPath;
    }

    public void setUsingPath(List<String> usingPath) {
        this.usingPath = usingPath;
    }

    public List<String> getIgnorePath() {
        return ignorePath;
    }

    public void setIgnorePath(List<String> ignorePath) {
        this.ignorePath = ignorePath;
    }

    public Long getUsingTotal() {
        return usingTotal;
    }

    public void setUsingTotal(Long usingTotal) {
        this.usingTotal = usingTotal;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "FileAbsolutePath{" +
                "basePath='" + basePath + '\'' +
                ", usingPath=" + usingPath +
                ", ignorePath=" + ignorePath +
                ", usingTotal=" + usingTotal +
                ", total=" + total +
                '}';
    }
}
