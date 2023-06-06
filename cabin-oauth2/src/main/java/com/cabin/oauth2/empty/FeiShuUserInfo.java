package com.cabin.oauth2.empty;

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
public class FeiShuUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    private String sub;
    private String picture;
    private String name;
    private String en_name;
    private String tenant_key;
    private String avatar_url;
    private String avatar_thumb;
    private String avatar_middle;
    private String avatar_big;
    private String open_id;
    private String union_id;
}
