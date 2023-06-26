package com.cabin.common.config.dingding;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2023/6/26 9:18
 */
@Data
@Component
@ConfigurationProperties(prefix = "dingdingalarm")
public class DingDingAlarmRobot {
    private String access_token;
    private String secret;
}
