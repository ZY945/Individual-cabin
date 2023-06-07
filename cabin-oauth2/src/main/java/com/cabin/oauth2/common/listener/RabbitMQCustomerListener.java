package com.cabin.oauth2.common.listener;

import com.alibaba.fastjson.JSON;
import com.cabin.oauth2.common.config.RabbitMQConfig;
import com.cabin.oauth2.common.enums.MQRoutingKeyEnum;
import com.cabin.oauth2.empty.mail.MailServiceImpl;
import com.cabin.oauth2.empty.mail.Vo.MailVo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQCustomerListener {

    @Autowired
    private MailServiceImpl mailService;

    //监听email队列
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_INFORM_EMAIL})
    public void receive_email(Object msg, Message message, Channel channel){
        //用于解决消息阻塞,手动确认
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        String body = new String(message.getBody());
        String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        if (receivedRoutingKey.equals(MQRoutingKeyEnum.SEND_CABIN_LOGIN_EMAIL.getRoutingKey())) {
            //登录验证码邮件
            MailVo mailVo = JSON.parseObject(message.getBody(), MailVo.class);
            mailService.sendCodeToMail(mailVo);
        }

    }

    //监听sms队列
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_INFORM_SMS})
    public void receive_sms(Object msg, Message message, Channel channel) {

    }

    //监听微信队列
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_INFORM_WX})
    public void receive_wx(Object msg, Message message, Channel channel) {

    }


    @RabbitListener(queues = {RabbitMQConfig.QUEUE_INFORM_DingDing})
    public void send_dingDing_error_log() {

    }
}
