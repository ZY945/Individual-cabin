package com.cabin.common.util.project;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 伍六七
 * @date 2023/6/26 15:06
 */
@Component
public class ApplicationInfo {
    @Value(value = "${spring.application.name}")
    private String applicationName;

    @Value(value = "${code.host}")
    private String host;

    @Value(value = "${server.port}")
    private Integer port;

    public String getApplicationName() {
        return applicationName;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
}
