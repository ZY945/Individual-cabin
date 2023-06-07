package com.cabin.oauth2.empty.feishu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2023/6/6 10:20
 */
@Data
@Component
@ConfigurationProperties("feishu.client")
public class FeiShuClient {
    private String codeUrlPrefix;
    private String tokenUrlPrefix;
    private String userUrlPrefix;
    private String clientId;
    private String clientSecret;
    private String redirectUrlGetToken;
}
