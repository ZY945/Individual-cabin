package com.cabin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
//        app.run(args);
//        logger.info("===============启动成功了===============");
    }
}



