package com.cabin.oauth2.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MQRoutingKeyEnum {

    /**
     * 格式
     * SEND_描述(登录、注册)_形式(EMAIL、SMS、WX)
     */
    SEND_CABIN_LOGIN_EMAIL("inform.cabin.login.email", "邮件发送登录验证码");

    private String routingKey;
    private String routingName;
}
