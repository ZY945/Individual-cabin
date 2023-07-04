package com.cabin.service;

import com.cabin.common.schedule.StatTask;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

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
    private ScheduledFuture<?> future;


    public void startCron() {
        String cron = "0/1 * * * * ?";
        //后面从数据库读取
        if (StringUtils.isBlank(cron)) {
            log.error("定时任务开启失败");
        } else {
            future = threadPoolTaskScheduler.schedule(new StatTask(), triggerContext -> Objects.requireNonNull(new CronTrigger(cron).nextExecution(triggerContext)));
            log.info("定时任务开启成功");
        }
    }

    public void stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        log.info("定时任务关闭");
    }


}
