package com.cabin.oauth2.service.Impl;

import com.alibaba.fastjson.JSON;
import com.cabin.oauth2.common.config.RabbitMQConfig;
import com.cabin.oauth2.common.enums.MQRoutingKeyEnum;
import com.cabin.oauth2.empty.mail.Vo.MailVo;
import com.cabin.oauth2.service.RegisterService;
import com.cabin.utils.commonUtil.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 伍六七
 * @date 2023/8/18 13:44
 */
@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String sendCode(String userEmail) {
        String code = StringUtil.creatCode(6);
        //消息队列发送验证码给用户邮件
        MailVo mailVo = new MailVo();
        String Text = "<span>以下是登录相关信息</span>" +
                "<table border=1>" +
                "        <tr>" +
                "            <td>验证码</td>" +
                "            <td>" + code + "</td>" +
                "        </tr>" +
                "    </table>";
        mailVo.setTo(userEmail)
                .setSub("登录验证码:" + code)
                .setText(Text);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_CABIN_INFORM, MQRoutingKeyEnum.SEND_CABIN_LOGIN_EMAIL.getRoutingKey(), JSON.toJSONString(mailVo));

        //存到redis里，限时1分钟内登录
        redisTemplate.opsForValue().set("email:Code:" + userEmail, code, 1, TimeUnit.MINUTES);

        return code;
    }
}
