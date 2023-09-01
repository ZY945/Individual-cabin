package com.cabin.common.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author 伍六七
 * @date 2023/8/29 21:52
 */
@Component
@DependsOn({"ApplicationContextUtil"})
@Slf4j
public class LogTask implements Runnable {
    //    @Value("${logPath}")
    private final static String logPath = "F:\\study\\code\\myProject\\Individual-cabin\\cabinLogs\\app\\spring.log";
    //    @Value("${sleepInterval}")
    private final static String sleepInterval = "1000";
    ;

    @Override
    public void run() {
        TailerListener listener = new TailerListenerAdapter() {
            @Override
            public void handle(String line) {
                try {
                    log.info("日志新增的内容为：" + line);
                } catch (Exception ex) {
                    log.error("发生异常：" + ex.getMessage());
                }
            }
        };
        Tailer tailer = new Tailer(new File(logPath), listener, Integer.parseInt(sleepInterval), true);
        tailer.run();
    }
}
