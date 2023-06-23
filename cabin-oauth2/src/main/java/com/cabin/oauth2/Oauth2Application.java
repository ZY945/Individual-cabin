package com.cabin.oauth2;

import com.cabin.oauth2.common.sslUtil.SslUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 伍六七
 * @date 2023/6/3 21:23
 */
@SpringBootApplication
public class Oauth2Application {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Oauth2Application.class);
        try {
            SslUtils.ignoreSsl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        springApplication.run(args);
    }
}
