package com.cabin.controller;

import com.cabin.common.schedule.email.EmailTask;
import com.cabin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 伍六七
 * @date 2023/8/5 18:33
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private TaskService taskService;

    private final String cron = "0/55 * *  * * ? ";

    @GetMapping("/test")
    public void readByEmail() {
        taskService.startCron(cron, new EmailTask(), "emailTask");
    }
}
