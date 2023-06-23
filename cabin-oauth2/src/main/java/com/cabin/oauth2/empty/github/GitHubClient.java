package com.cabin.oauth2.empty.github;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2023/4/26 9:35
 */
@Data
@Component
@ConfigurationProperties("github.client")
public class GitHubClient {
    private String codeUrlPrefix;
    private String tokenUrlPrefix;
    private String userUrlPrefix;
    private String clientId;
    private String clientSecret;
    private String redirectUrlGetToken;
}
