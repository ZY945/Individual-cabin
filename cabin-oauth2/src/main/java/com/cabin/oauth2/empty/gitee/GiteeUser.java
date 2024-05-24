package com.cabin.oauth2.empty.gitee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 伍六七
 * @date 2023/6/24 23:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cabin_user_oauth_gitee")
public class GiteeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "gitee_user_id")
    private Long giteeUserId;
    private String login;
    private String name;
    private String avatar_url;
    private String url;
    private String html_url;
    private String remark;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private String blog;
    private String weibo;
    private String bio;
    private Integer public_repos;
    private Integer public_gists;
    private Integer followers;
    private Integer following;
    private Integer stared;
    private Integer watched;
    private Date created_at;
    private Date updated_at;
    private String email;
}
