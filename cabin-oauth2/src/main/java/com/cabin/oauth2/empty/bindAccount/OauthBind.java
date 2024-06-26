package com.cabin.oauth2.empty.bindAccount;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/9 14:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cabin_user_bind")
public class OauthBind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    @Column(name = "feishu_openid")
    private String feiShuOpenId;

    @Column(name = "github_userid")
    private Long gitHubUserId;

    @Column(name = "gitee_userid")
    private Long giteeUserId;
}
