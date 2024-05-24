package com.cabin.controller;

import com.cabin.common.schedule.CpuUsageTask;
import com.cabin.common.schedule.MemoryTask;
import com.cabin.common.schedule.StatTask;
import com.cabin.common.schedule.UptimeTask;
import com.cabin.service.TaskSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/7/3 17:47
 */
@RestController
@RequestMapping("/task")
public class UploadController {
    @Autowired
    private TaskSchedulerService taskSchedulerService;

    private final String cron = "0/1 * * * * ?";
//    private final String cron = "0/3 * * * * ?";

    @GetMapping("/all/start")
    public void allStart() {
        taskSchedulerService.startCron(cron, new MemoryTask(), "memoryTask");
        taskSchedulerService.startCron(cron, new StatTask(), "statTask");
        taskSchedulerService.startCron(cron, new UptimeTask(), "uptimeTask");
    }

    @GetMapping("/all/stop")
    public void allStop() {
        taskSchedulerService.stopCron("memoryTask");
        taskSchedulerService.stopCron("processorTask");
        taskSchedulerService.stopCron("statTask");
    }

    @GetMapping("/memory/start")
    public void memoryStart() {
        taskSchedulerService.startCron(cron, new MemoryTask(), "memoryTask");
    }

    @GetMapping("/cpuUsage/start")
    public void cpuUsageStart() {
        taskSchedulerService.startCron("0/3 * * * * ?", new CpuUsageTask(), "cpuUsageTask");
    }

    @GetMapping("/stat/start")
    public void statStart() {

        taskSchedulerService.startCron(cron, new StatTask(), "statTask");
    }

    @GetMapping("/uptime/start")
    public void uptimeStart() {
        taskSchedulerService.startCron(cron, new UptimeTask(), "uptimeTask");
    }


    @GetMapping("/memory/stop")
    public void memoryStop() {
        taskSchedulerService.stopCron("memoryTask");
    }

    @GetMapping("/cpuUsage/stop")
    public void cpuUsageStop() {
        taskSchedulerService.stopCron("cpuUsageTask");
    }

    @GetMapping("/processor/stop")
    public void processorStop() {
        taskSchedulerService.stopCron("processorTask");
    }

    @GetMapping("/stat/stop")
    public void statStop() {
        taskSchedulerService.stopCron("statTask");
    }

}
