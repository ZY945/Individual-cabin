package com.cabin.common.handler;

import com.alibaba.fastjson2.JSON;
import com.cabin.common.config.RabbitMQConfig;
import com.cabin.common.emum.MQRoutingKeyEnum;
import com.cabin.entity.response.Result;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiRobotSendRequest.Markdown;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 伍六七
 * @date 2023/6/26 9:37
 */
@Slf4j
@RestControllerAdvice()
public class GlobalExceptionHandler {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        try {
            //发送报警
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            //获取当前请求对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest req = attributes.getRequest();

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat();
            Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle("监控报警通知");
            markdown.setText("## 监控报警通知\n" +
                    "\n" +
                    "> 请求方式：\n" + req.getMethod() + "\n" +
                    "\n" +
                    "> 请求地址：\n" + req.getRequestURI() + "\n" +
                    "\n" +
                    "> 异常时间：\n" + sdf.format(date) + "\n" +
                    "\n" +
                    "> 异常：\n" + e);
            //第一个参数是交换机,第二个参数是你要传的key,第三个参数是消息
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_CABIN_ALARM, MQRoutingKeyEnum.SEND_CABIN_ALARM_DINGDING.getRoutingKey(), JSON.toJSONString(markdown));

        } catch (Exception ee) {
            log.error("发送异常信息接口异常", ee);
        }
        return Result.serverFail("发送异常接口失败");
    }
}
