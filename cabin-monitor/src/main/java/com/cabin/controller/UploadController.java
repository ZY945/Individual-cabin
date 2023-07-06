package com.cabin.controller;

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

    @GetMapping("/memory/start")
    public void memoryStart() {
        taskService.startCron(cron, new MemoryTask(), "memoryTask");
    }

//    @GetMapping("/processor/start")
//    public void processorStart() {
//        taskService.startCron(cron, new ProcessorTask(), "processorTask");
//    }

    @GetMapping("/stat/start")
    public void statStart() {

        taskService.startCron(cron, new StatTask(), "statTask");
    }

    @GetMapping("/uptime/start")
    public void uptimeStart() {
        String cron = "0/1 * * * * ?";

        taskService.startCron(cron, new UptimeTask(), "uptimeTask");
    }


    @GetMapping("/memory/stop")
    public void memoryStop() {
        taskService.stopCron("memoryTask");
    }

    @GetMapping("/processor/stop")
    public void processorStop() {
        taskService.stopCron("processorTask");
    }

    @GetMapping("/stat/stop")
    public void statStop() {
        taskService.stopCron("statTask");
    }

//    @GetMapping("/uptime/stop")
//    public void uptimeStop() {
//        taskService.stopCron("uptimeTask");
//    }
}
