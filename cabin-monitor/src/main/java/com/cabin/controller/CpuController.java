package com.cabin.controller;

import com.cabin.common.mail.Vo.MailVo;
import com.cabin.common.rabbitmq.MQRoutingKeyEnum;
import com.cabin.common.rabbitmq.RabbitMQConfig;
import com.cabin.empty.vo.CPUStatVo;
import com.cabin.service.QueryService;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import com.cabin.utils.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/6/30 11:10
 */
@RestController
@RequestMapping("/cpu")
@Slf4j
public class CpuController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${notice.email}")
    private String userEmail;

    @GetMapping("/usage")
    public Result<List<String>> getStat() throws UnknownHostException {
        String bucket = "bucket";
        String[] cpuMeasurement = new String[]{"CPU1Stat", "CPU2Stat", "CPU3Stat"};
        ArrayList<String> list = new ArrayList<>();
        //根据cpu情况手动修改,后续可能自动判断
        List<CPUStatVo> secondCpuBeforeOne = queryService.getOneSecondCpuBefore(bucket, cpuMeasurement, 0L);
        List<CPUStatVo> secondCpuBeforeTwo = queryService.getOneSecondCpuBefore(bucket, cpuMeasurement, 1L);
        for (int i = 0; i < 3; i++) {
            CPUStatVo one = secondCpuBeforeOne.get(i);
            CPUStatVo two = secondCpuBeforeTwo.get(i);
            // 计算总的Cpu时间片totalCpuTime

            long totalCpuTime1 = one.getTotalCpuTime();
            long cpuIdle1 = one.getIdleCpuTime();

            // 等待一段时间（例如1秒）

            long totalCpuTime2 = two.getTotalCpuTime();
            long cpuIdle2 = two.getIdleCpuTime();
            long totalDiff = totalCpuTime2 - totalCpuTime1;
            long idleDiff = cpuIdle2 - cpuIdle1;

            double cpuUsage = (totalDiff - idleDiff) * 100.0 / totalDiff;
            list.add(cpuUsage + "%");
            // TODO 后续可以设置阈值
            if (cpuUsage > 50) {
                String cpuName = one.getCpuName();
                InetAddress localhost = InetAddress.getLocalHost();
                String ipAddress = localhost.getHostAddress();


                //消息队列发送验证码给用户邮件
                MailVo mailVo = new MailVo();
                String Text =
                        "报警服务器:" + ipAddress + "<br>" +
                                "cpu型号:" + cpuName + "<br>" +
                                "cpu占用率:" + cpuUsage + "%";
                mailVo.setTo(userEmail)
                        .setSub("服务器报警-邮件通知")
                        .setText(Text);
                log.error(ipAddress + "的" + cpuName + "报警");
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_CABIN_MONITOR, MQRoutingKeyEnum.SEND_CABIN_MONITOR_EMAIL.getRoutingKey(), JacksonUtils.writeValueAsString(mailVo));
                // TODO 钉钉报警
            }
        }
        return Result.success(list, "获取cpu利用率");
    }
}
