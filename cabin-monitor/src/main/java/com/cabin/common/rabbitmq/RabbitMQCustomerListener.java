package com.cabin.common.rabbitmq;

import com.cabin.common.mail.MailServiceImpl;
import com.cabin.common.mail.Vo.MailVo;
import com.cabin.notice.util.DingTalkHelper;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import com.dingtalk.api.request.OapiRobotSendRequest.Markdown;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RabbitMQCustomerListener {

    @Autowired
    private MailServiceImpl mailService;

    @Resource(name = "DingTalkHelper")
    private DingTalkHelper dingTalkHelper;

    //监听email队列
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_MONITOR_EMAIL})
    public void receive_email(Object msg, Message message, Channel channel) throws IOException {
        //用于解决消息阻塞,手动确认
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        String body = new String(message.getBody());
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (receivedRoutingKey.equals(MQRoutingKeyEnum.SEND_CABIN_MONITOR_EMAIL.getRoutingKey())) {
            //发送报警通知
            MailVo mailVo = JacksonUtils.convertValue(message.getBody(), MailVo.class);
            mailService.sendMDToMail(mailVo);
        }

    }

    //监听dingding队列
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_MONITOR_DingDing})
    public void send_dingDing_error_log(Object msg, Message message, Channel channel) throws IOException {
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (receivedRoutingKey.equals(MQRoutingKeyEnum.SEND_CABIN_MONITOR_DING.getRoutingKey())) {
            //发送报警通知,
            Markdown markdown = JacksonUtils.convertValue(message.getBody(), Markdown.class);
            dingTalkHelper.sendAlarmByMarkdown(markdown, null, true);
        }
    }
}
