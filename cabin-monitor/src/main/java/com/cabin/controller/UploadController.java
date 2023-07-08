package com.cabin.controller;

import com.cabin.common.schedule.CpuUsageTask;
import com.cabin.common.schedule.MemoryTask;
import com.cabin.common.schedule.StatTask;
import com.cabin.common.schedule.UptimeTask;
import com.cabin.service.TaskService;
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
    private TaskService taskService;

    private final String cron = "0/1 * * * * ?";
//    private final String cron = "0/3 * * * * ?";

    @GetMapping("/all/start")
    public void allStart() {
        taskService.startCron(cron, new MemoryTask(), "memoryTask");
        taskService.startCron(cron, new StatTask(), "statTask");
        taskService.startCron(cron, new UptimeTask(), "uptimeTask");
    }

    @GetMapping("/all/stop")
    public void allStop() {
        taskService.stopCron("memoryTask");
        taskService.stopCron("processorTask");
        taskService.stopCron("statTask");
    }

    @GetMapping("/memory/start")
    public void memoryStart() {
        taskService.startCron(cron, new MemoryTask(), "memoryTask");
    }

    @GetMapping("/cpuUsage/start")
    public void cpuUsageStart() {
        taskService.startCron("0/3 * * * * ?", new CpuUsageTask(), "cpuUsageTask");
//        CpuUsageTask cpuUsageTask = new CpuUsageTask();
//        cpuUsageTask.run();
    }

    @GetMapping("/stat/start")
    public void statStart() {

        taskService.startCron(cron, new StatTask(), "statTask");
    }

    @GetMapping("/uptime/start")
    public void uptimeStart() {
        taskService.startCron(cron, new UptimeTask(), "uptimeTask");
    }


    @GetMapping("/memory/stop")
    public void memoryStop() {
        taskService.stopCron("memoryTask");
    }

    @GetMapping("/cpuUsage/stop")
    public void cpuUsageStop() {
        taskService.stopCron("cpuUsageTask");
    }

    @GetMapping("/processor/stop")
    public void processorStop() {
        taskService.stopCron("processorTask");
    }

    @GetMapping("/stat/stop")
    public void statStop() {
        taskService.stopCron("statTask");
    }

}
