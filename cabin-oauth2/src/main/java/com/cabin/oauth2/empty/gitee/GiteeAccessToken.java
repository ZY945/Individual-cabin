package com.cabin.oauth2.empty.gitee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 伍六七
 * @date 2023/6/24 23:07
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiteeAccessToken {
    private String access_token;
    //    private String refresh_token;
    private String token_type;
    //    private int expires_in;
//    private int refresh_expires_in;
    private String scope;
}
