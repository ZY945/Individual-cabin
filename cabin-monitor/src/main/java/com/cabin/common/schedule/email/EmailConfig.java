package com.cabin.common.schedule.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * HOST PORT ISSSL
 * imap.163.com 993 true
 * imap.qq.com 993 true
 */
@Data
@Component
@ConfigurationProperties(prefix = "schedule.mail")
public class EmailConfig {
    private String username;
    private String password;
    private String host;
    private int port;
}
