package com.cabin.common.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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