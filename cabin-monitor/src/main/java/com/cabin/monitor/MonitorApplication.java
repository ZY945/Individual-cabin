package com.cabin.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 伍六七
 * @date 2023/6/30 11:07
 */
@SpringBootApplication
public class MonitorApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MonitorApplication.class);
        springApplication.run(args);
    }
}
