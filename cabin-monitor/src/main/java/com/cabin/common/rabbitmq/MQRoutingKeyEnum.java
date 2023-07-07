package com.cabin.common.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MQRoutingKeyEnum {

    /**
     * 格式
     * SEND_描述(登录、注册)_形式(EMAIL、SMS、WX)
     */
    SEND_CABIN_MONITOR_EMAIL("monitor.cabin.email", "邮件发送服务器报警");

    private String routingKey;
    private String routingName;
}
