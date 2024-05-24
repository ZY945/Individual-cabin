package com.cabin.common.schedule;

import com.cabin.common.app.ApplicationInfo;
import com.cabin.common.mail.Vo.MailVo;
import com.cabin.common.rabbitmq.MQRoutingKeyEnum;
import com.cabin.common.rabbitmq.RabbitMQConfig;
import com.cabin.common.util.ApplicationContextUtil;
import com.cabin.empty.notice.NoticeInfo;
import com.cabin.empty.vo.CPUStatVo;
import com.cabin.influxDB.util.InfluxDBTemplate;
import com.cabin.service.QueryService;
import com.cabin.service.TaskSchedulerService;
import com.cabin.utils.dateUtil.DateUtil;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import com.dingtalk.api.request.OapiRobotSendRequest.Markdown;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/7/8 18:02
 */
@Component
@DependsOn({"ApplicationContextUtil"})
@Slf4j
public class CpuUsageTask implements Runnable {


    private final ApplicationInfo applicationInfo = (ApplicationInfo) ApplicationContextUtil.getBean("applicationInfo");
    private final NoticeInfo noticeInfo = (NoticeInfo) ApplicationContextUtil.getBean("noticeInfo");
    private final InfluxDBTemplate template = (InfluxDBTemplate) ApplicationContextUtil.getBean("InfluxDBTemplate");
    private final RabbitTemplate rabbitTemplate = (RabbitTemplate) ApplicationContextUtil.getBean("rabbitTemplate");
    private final QueryService queryService = (QueryService) ApplicationContextUtil.getBean("queryService");
    private final TaskSchedulerService taskService = (TaskSchedulerService) ApplicationContextUtil.getBean("taskSchedulerService");

    public Boolean writeStat() throws JsonProcessingException, UnknownHostException {
        HashMap<String, Object> map = new HashMap<>();
        boolean state = false;
        String bucket = "bucket";
        String[] cpuMeasurement = new String[]{"cpuStat", "cpu0Stat", "cpu1Stat"};
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
            String cpuUsageName;
            if (i == 0) {
                cpuUsageName = "cpuUsage";
            } else {
                cpuUsageName = "cpu" + (i - 1) + "Usage";
            }
            map.put(cpuUsageName, cpuUsage + "%");
            // TODO 后续可以设置阈值
            if (cpuUsage > 30) {
                state = true;
                // InetAddress.getLocalHost()会获取docker容器的ip
                // InetAddress localhost = InetAddress.getLocalHost();
                // String ipAddress = localhost.getHostAddress();
                Instant nowInstant = DateUtil.getNowInstant();
                Markdown markdown = new Markdown();
                markdown.setTitle("监控报警通知");
                markdown.setText("## 监控报警通知\n" +
                        "\n" +
                        "> **项目名**：\n" + applicationInfo.getApplicationName() + "\n" +
                        "\n" +
                        "> **ip**：\n" + applicationInfo.getHost() + "\n" +
                        "\n" +
                        "> **port**：\n" + applicationInfo.getPort() + "\n" +
                        "\n" +
                        "> **cpu型号**：\n" + cpuUsageName + "\n" +
                        "\n" +
                        "> **cpu占用率**：\n" + cpuUsage + "%" + "\n" +
                        "\n" +
                        "> **当前时间**：\n" + nowInstant);
                //第一个参数是交换机,第二个参数是你要传的key,第三个参数是消息
                //发送钉钉通知
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_CABIN_MONITOR, MQRoutingKeyEnum.SEND_CABIN_MONITOR_DING.getRoutingKey(), JacksonUtils.writeValueAsString(markdown));

                //消息队列发送验证码给用户邮件
                MailVo mailVo = new MailVo();
                mailVo.setTo(noticeInfo.getUserEmail())
                        .setSub("服务器报警-邮件通知")
                        .setMarkdown(markdown);
                log.error(applicationInfo.getHost() + "的" + cpuUsageName + "报警");
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
