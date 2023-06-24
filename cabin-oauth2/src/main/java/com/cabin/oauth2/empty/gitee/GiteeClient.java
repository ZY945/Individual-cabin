package com.cabin.oauth2.empty.gitee;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2023/6/24 23:07
 */
@Data
@Component
@ConfigurationProperties("gitee.client")
public class GiteeClient {
    private String codeUrlPrefix;
    private String tokenUrlPrefix;
    private String userUrlPrefix;
    private String clientId;
    private String clientSecret;
    private String redirectUrlGetToken;
}
