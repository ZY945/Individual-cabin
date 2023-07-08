package com.cabin.common.schedule;

import com.cabin.common.mail.Vo.MailVo;
import com.cabin.common.rabbitmq.MQRoutingKeyEnum;
import com.cabin.common.rabbitmq.RabbitMQConfig;
import com.cabin.common.util.ApplicationContextUtil;
import com.cabin.empty.notice.NoticeInfo;
import com.cabin.empty.vo.CPUStatVo;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.service.QueryService;
import com.cabin.service.TaskService;
import com.cabin.util.procUtil.ProcUtil;
import com.cabin.utils.dateUtil.DateUtil;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/7/8 18:02
 */
@Component
@DependsOn({"ApplicationContextUtil", "NoticeInfo"})
@Slf4j
public class CpuUsageTask implements Runnable {


    private final InfluxDBTemplate template = (InfluxDBTemplate) ApplicationContextUtil.getBean("InfluxDBTemplate");
    private final NoticeInfo noticeInfo = (NoticeInfo) ApplicationContextUtil.getBean("NoticeInfo");
    private final RabbitTemplate rabbitTemplate = (RabbitTemplate) ApplicationContextUtil.getBean("rabbitTemplate");
    private final QueryService queryService = (QueryService) ApplicationContextUtil.getBean("queryService");
    private final TaskService taskService = (TaskService) ApplicationContextUtil.getBean("taskService");

    public Boolean writeStat() throws JsonProcessingException, UnknownHostException {
        HashMap<String, Object> map = new HashMap<>();
        Boolean state = false;
        String bucket = "bucket";
        String[] cpuMeasurement = new String[]{"CPU1Stat", "CPU2Stat", "CPU3Stat"};
        ArrayList<String> list = new ArrayList<>();
        //根据cpu情况手动修改,后续可能自动判断
        List<CPUStatVo> secondCpuBeforeOne = queryService.getOneSecondCpuBefore(bucket, cpuMeasurement, 0L);
        List<CPUStatVo> secondCpuBeforeTwo = queryService.getOneSecondCpuBefore(bucket, cpuMeasurement, 1L);
        Instant now = DateUtil.getNowInstant();

        for (int i = 0; i < 3; i++) {
            CPUStatVo one = secondCpuBeforeOne.get(i);
            CPUStatVo two = secondCpuBeforeTwo.get(i);
            // 计算总的Cpu时间片totalCpuTime

            long totalCpuTime1 = one.getTotalCpuTime();
            long oldCpuIdle2 = one.getIdleCpuTime();

            // 等待一段时间（例如1秒）

            long totalCpuTime2 = two.getTotalCpuTime();
            long nowCpuIdle2 = two.getIdleCpuTime();
            long totalDiff = totalCpuTime1 - totalCpuTime2;
            long idleDiff = oldCpuIdle2 - nowCpuIdle2;

            double cpuUsage = (totalDiff - idleDiff) * 100.0 / totalDiff;
            list.add(cpuUsage + "%");
            map.put("cpuUsage" + i, cpuUsage + "%");
            // TODO 后续可以设置阈值
            if (cpuUsage > 30) {
                state = true;
                String cpuName = one.getCpuName();
                InetAddress localhost = InetAddress.getLocalHost();
                String ipAddress = localhost.getHostAddress();


                //消息队列发送验证码给用户邮件
                MailVo mailVo = new MailVo();
                String Text =
                        "报警服务器:" + ipAddress + "<br>" +
                                "cpu型号:" + cpuName + "<br>" +
                                "cpu占用率:" + cpuUsage + "%";
                mailVo.setTo(noticeInfo.getUserEmail())
                        .setSub("服务器报警-邮件通知")
                        .setText(Text);
                log.error(ipAddress + "的" + cpuName + "报警");
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_CABIN_MONITOR, MQRoutingKeyEnum.SEND_CABIN_MONITOR_EMAIL.getRoutingKey(), JacksonUtils.writeValueAsString(mailVo));
                // TODO 钉钉报警
            }
        }
        Point point = Point.measurement("cpuUsage")
                .addFields(map)
                .time(now, WritePrecision.S);
        template.writePoint(point);
        return state;
    }

    @Override
    public void run() {
        try {
            Boolean state = writeStat();
            log.info("一次定时");
            if (state) {
                log.error("报警了,停止监控");
                taskService.stopCron("cpuUsageTask");
            }
        } catch (JsonProcessingException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
