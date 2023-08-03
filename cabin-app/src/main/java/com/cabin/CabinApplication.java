package com.cabin;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

/**
 * @author 伍六七
 * @date 2023/5/21 22:00
 */
@SpringBootApplication//
//@EnableScheduling
public class CabinApplication {
    public static void main(String[] args) {
//        logger.info("===============项目启动了===============");
//        SpringApplication app = new SpringApplication(CabinApplication.class);
        //允许循环依赖
//        app.setAllowCircularReferences(true);
        try {

            SpringApplication.run(CabinApplication.class, args);
        } catch (Throwable e) {
            System.out.println(e);
        }
    }

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.parse("5000MB")); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("5000MB"));
        return factory.createMultipartConfig();
    }
}



