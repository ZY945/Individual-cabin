package com.cabin.controller;

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
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/start")
    public void start() {
        taskService.startCron();
    }

    @GetMapping("/stop")
    public void stop() {
        taskService.stopCron();
    }
}
