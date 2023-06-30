package com.cabin.monitor.util.shell;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author 伍六七
 * @date 2023/6/30 13:51
 */
@Slf4j
@Component
public class ShellUtil {
    public BufferedReader cat(String path) {
        return getBufferByExec("cat " + path);
    }

    /**
     * 使用 exec 调用Cat脚本
     *
     * @param path
     */
    private static BufferedReader getBufferByExec(String path) {
        BufferedReader reader = null;
        try {
            Process process = Runtime.getRuntime().exec(path);
            int exitValue = process.waitFor();
            if (0 != exitValue) {
                log.error("call shell failed. error code is :" + exitValue);
            }
            // 返回值
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (Throwable e) {
            log.error("call shell failed. " + e);
        }
        return reader;
    }

}
