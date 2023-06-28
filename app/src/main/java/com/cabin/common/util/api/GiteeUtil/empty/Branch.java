package com.cabin.common.util.api.GiteeUtil.empty;

/**
 * @author 伍六七
 * @date 2023/5/17 9:49
 * [{"name":"dev",
 * "commit":{"sha":"111111111111111111111111111111",
 * "url":"https://gitee.com/api/v5/repos/{owner}/{repo}/commits/111111111111111111111111111111"
 * },
 * "protected":false,
 * "protection_url":"https://gitee.com/api/v5/repos/{owner}/{repo}/branches/dev/protection"},
 * ]
 */
public class Branch {
    private String name;
    private Commit commit;
    // TODO 原参数为 protected,不知道能否获取
    private boolean isProtected;
    private String protection_url;

    @Override
    public String toString() {
        return "Branch{" +
                "name='" + name + '\'' +
                ", commit=" + commit +
                ", isProtected=" + isProtected +
                ", protection_url='" + protection_url + '\'' +
                '}';
    }

    public Branch() {
    }

    public Branch(String name, Commit commit, boolean isProtected, String protection_url) {
        this.name = name;
        this.commit = commit;
        this.isProtected = isProtected;
        this.protection_url = protection_url;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public String getProtection_url() {
        return protection_url;
    }

    public void setProtection_url(String protection_url) {
        this.protection_url = protection_url;
    }
}
