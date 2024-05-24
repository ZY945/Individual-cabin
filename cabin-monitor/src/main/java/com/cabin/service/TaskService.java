package com.cabin.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author 伍六七
 * @date 2023/8/30 20:55
 */
@Service
@Slf4j
public class TaskService extends ThreadPoolTaskExecutor {


    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Async
    public void task(Runnable task, String taskName) {
        try {
            Future<?> submit = threadPoolTaskExecutor.submit(task);
            Object o = submit.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
