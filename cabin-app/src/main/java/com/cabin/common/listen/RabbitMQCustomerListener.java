package com.cabin.common.listen;


import com.alibaba.fastjson2.JSON;
import com.cabin.common.config.RabbitMQConfig;
import com.cabin.common.enums.MQRoutingKeyEnum;
import com.cabin.common.util.dingding.DingTalkHelper;
import com.cabin.common.util.mail.MailServiceImpl;
import com.cabin.common.util.mail.Vo.MailVo;
import com.dingtalk.api.request.OapiRobotSendRequest.Markdown;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听是以队列为单位，然后根据获取的不同key去执行不同方法
 */
@Slf4j
@Component
public class RabbitMQCustomerListener {

    @Autowired
    private DingTalkHelper dingTalkHelper;
    @Autowired
    private MailServiceImpl mailService;

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_ALARM_DingDing})
    public void sendDingDingAlarm(Object object, Message message, Channel channel) {
        //用于解决消息阻塞,手动确认
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        String body = new String(message.getBody());
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (receivedRoutingKey.equals(MQRoutingKeyEnum.SEND_CABIN_ALARM_DINGDING.getRoutingKey())) {
            Markdown markdown = JSON.parseObject(message.getBody(), Markdown.class);
            dingTalkHelper.sendAlarmByMarkdown(markdown, null, true);
        }
    }

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_ALARM_EMAIL})
    public void sendEmailAlarm(Object object, Message message, Channel channel) {
        //用于解决消息阻塞,手动确认
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        String body = new String(message.getBody());
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (receivedRoutingKey.equals(MQRoutingKeyEnum.SEND_CABIN_ALARM_EMAIL.getRoutingKey())) {
            MailVo mailVo = JSON.parseObject(message.getBody(), MailVo.class);
            mailService.sendAlarmToMail(mailVo);
        }
    }

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_INFORM_API_DingDing})
    public void sendDingDingInform(Object object, Message message, Channel channel) {
        //用于解决消息阻塞,手动确认
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        String body = new String(message.getBody());
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (receivedRoutingKey.equals(MQRoutingKeyEnum.SEND_CABIN_INFORM_DINGDING.getRoutingKey())) {
            Markdown markdown = JSON.parseObject(message.getBody(), Markdown.class);
            dingTalkHelper.sendAlarmByMarkdown(markdown, null, true);
        }
    }
}
