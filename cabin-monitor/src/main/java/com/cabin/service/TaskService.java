package com.cabin.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/**
 * @author 伍六七
 * @date 2023/7/3 17:40
 */
@Service
@Slf4j
@DependsOn("threadPoolTaskScheduler")
public class TaskService {

    @Resource
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final Map<String, ScheduledFuture<?>> taskMap = new HashMap<>();


    public synchronized void startCron(String cron, Runnable task, String taskName) {
        //后面从数据库设置和读取
        if (taskMap.size() > 2) {
            log.error("定时任务大于2,请稍后在设置");
        }
        if (StringUtils.isBlank(cron) || taskMap.containsKey(taskName)) {
            log.error("定时任务开启失败");
        } else {
            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(task, triggerContext -> Objects.requireNonNull(new CronTrigger(cron).nextExecution(triggerContext)));
            taskMap.put(taskName, future);
            log.info("定时任务开启成功");
        }
    }

    public synchronized void stopCron(String taskName) {
        ScheduledFuture<?> future = taskMap.get(taskName);
        if (future != null) {
            future.cancel(true);
            taskMap.remove(taskName);
            log.info("定时任务 {} 已关闭", taskName);
        } else {
            log.error("找不到指定的定时任务 {}", taskName);
        }
    }


}
