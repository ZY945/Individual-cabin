package com.cabin.oauth2.empty.feishu;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/6 16:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cabin_user_oauth_feishu")
public class FeiShuUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sub;

    private String picture;

    private String name;

    @Column(name = "en_name")
    @JsonProperty("en_name")
    private String enName;

    @Column(name = "tenant_key")
    @JsonProperty("tenant_key")
    private String tenantKey;

    @Column(name = "avatar_url")
    @JsonProperty("avatar_url")
    private String avatarUrl;

    @Column(name = "avatar_thumb")
    @JsonProperty("avatar_thumb")
    private String avatarThumb;

    @Column(name = "avatar_middle")
    @JsonProperty("avatar_middle")
    private String avatarMiddle;

    @Column(name = "avatar_big")
    @JsonProperty("avatar_big")
    private String avatarMig;

    @Column(name = "open_id")
    @JsonProperty("open_id")
    private String openId;

    @Column(name = "union_id")
    @JsonProperty("union_id")
    private String unionId;

}
