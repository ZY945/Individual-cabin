package com.cabin.admin.rocketmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/rocketmq")
public class RocketMQController {

    @Autowired
    private RocketMQImpl rocketMQImpl;

    /**
     * 开启监控,首次启动会自动加载
     */
    @GetMapping("/start")
    public String start() {
        rocketMQImpl.init();
        return "开启监控";
    }


    /**
     * 关闭监控
     */
    @GetMapping("/shutdown")
    public String shutdown() {
        rocketMQImpl.shutdown();
        return "关闭监控";
    }

    /**
     * 监控
     */
    @GetMapping("/monitor")
    public HashMap<String, Object> getMonitor() {
        HashMap<String, Object> topics = null;
        try {
            topics = rocketMQImpl.getMonitor();
            return topics;
        } catch (Exception e) {

            e.printStackTrace();
        }
        topics.put("msg","服务未开启");
        return topics;
    }

    /**
     * 获取所有 Topic 列表
     */
    @GetMapping("/topics")
    public Set<String> getTopics() {
        Set<String> topics;
        try {
            topics = rocketMQImpl.getTopics();
            return topics;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建 Topic
     */
    @PostMapping("/topics")
    public String createTopic(@RequestParam String topicName) throws Exception {
        String msg;
        boolean create = rocketMQImpl.createTopic(topicName);
        if (create) {
            msg = "创建成功,主题为" + topicName;
        } else {
            msg = "创建失败,主题已存在";
            //TODO 日志输出
            // ("已存在名称为" + topicName + "的主题，无法执行添加操作！");
        }
        return msg;
    }

    /**
     * 删除 Topic
     */
    @DeleteMapping("/topics/{topicName}")
    public String deleteTopic(@PathVariable String topicName) throws Exception {
        String msg;
        boolean del = rocketMQImpl.deleteTopicByName(topicName);
        if (del) {
            msg = "删除成功";
        } else {
            msg = "该主题不存在";
            //TODO 日志输出
            // ("没有名称为" + topicName + "的主题，无法执行删除操作！");
        }
        return msg;
    }
}
