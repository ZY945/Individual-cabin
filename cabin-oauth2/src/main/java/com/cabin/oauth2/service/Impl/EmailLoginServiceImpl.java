package com.cabin.oauth2.service.Impl;

import com.alibaba.fastjson.JSON;
import com.cabin.oauth2.common.config.RabbitMQConfig;
import com.cabin.oauth2.common.enums.MQRoutingKeyEnum;
import com.cabin.oauth2.empty.User;
import com.cabin.oauth2.empty.mail.Vo.MailVo;
import com.cabin.oauth2.repository.UserRepository;
import com.cabin.oauth2.service.EmailLoginService;
import com.cabin.utils.commonUtil.Base64Util;
import com.cabin.utils.commonUtil.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 伍六七
 * @date 2023/1/26 19:09
 */
@Service
@Slf4j
public class EmailLoginServiceImpl implements EmailLoginService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public String login(String userEmail, String code) {
        String redisCode = null;
        String token = null;
        redisCode = redisTemplate.opsForValue().get("email:Code:" + userEmail);
        if (code.equals(redisCode)) {
            //TODO 验证成功 jwt生成
            token = getAndSaveToken(userEmail);
            redisTemplate.delete("email:Code:" + userEmail);
        } else {
            return token;
        }
        User userByEmail = userRepository.getUserByEmail(userEmail);
        Date now = new Date();
        if (userByEmail == null) {
            User user = new User();
            user.setEmail(userEmail);
            user.setCreatTime(now);
            user.setLastLoginTime(now);
            userRepository.save(user);
        } else {
            userByEmail.setLastLoginTime(now);
            userRepository.save(userByEmail);
        }
        return token;
    }

    @Override
    public String getAndSaveToken(String userEmail) {
        //TODO 目前key是email+随机数,value是userId,都用base64加密
        String token = null;
        String userIdEncoder = null;
        String random = StringUtil.creatCode(6);
        try {
            token = Base64Util.encoderGetStrByByte(userEmail + random);
            User user = userRepository.getUserByEmail(userEmail);
            if (user == null) {
                log.info("该用户不存在:userEmail=" + userEmail);
                return null;
            }
            userIdEncoder = Base64Util.encoderGetStrByByte(String.valueOf(user.getId()));

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        redisTemplate.opsForValue().set("user:token:" + token, userIdEncoder, 5, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public String savePassWord(String token, String passWord) {
        // TODO 先解析token获取用户id
        // 然后验证用户,同时保存
        return null;
    }


    @Override
    public String refreshToken(String userEmail, String token) {
        //todo 未完善
        String newToken = redisTemplate.opsForValue().get("token:" + token);
        if (token.equals(newToken)) {
            return token;
        }
        return null;
    }

    @Override
    public Boolean logout(String token) {
        return redisTemplate.delete("user:token:" + token);
    }
}
