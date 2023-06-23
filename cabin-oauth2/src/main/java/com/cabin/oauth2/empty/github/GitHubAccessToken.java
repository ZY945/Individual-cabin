package com.cabin.oauth2.empty.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GitHubAccessToken {
    private String access_token;
    //    private String refresh_token;
    private String token_type;
    //    private int expires_in;
//    private int refresh_expires_in;
    private String scope;
}