package com.cabin.common.Task.Schedule;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "schedule.mail")
public class EmailConfig {
    String username;
    String password;
}
