package com.cabin.notice.empty;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2023/6/22 22:38
 */
@Data
@Component
@ConfigurationProperties(prefix = "dingding")
public class DingDingConnect {
    private String access_token;
    private String secret;
    private String proxyHost;
    private Integer proxyPort;
}
