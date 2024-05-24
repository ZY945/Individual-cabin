package com.cabin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MQRoutingKeyEnum {

    /**
     * 格式
     * SEND_描述(登录、注册)_形式(EMAIL、SMS、WX)
     */
    SEND_CABIN_ALARM_DINGDING("alarm.cabin.dingding", "发送钉钉报警"),
    SEND_CABIN_ALARM_EMAIL("alarm.cabin.email", "发送钉钉报警"),
    SEND_CABIN_INFORM_DINGDING("inform.api.cabin.dingding", "发送接口调用信息");

    private String routingKey;
    private String routingName;
}
