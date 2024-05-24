package com.cabin.common.aop;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.blueconic.browscap.BrowsCapField;
import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.UserAgentParser;
import com.cabin.common.config.RabbitMQConfig;
import com.cabin.common.enums.MQRoutingKeyEnum;
import com.cabin.common.util.request.DeviceUtil;
import com.cabin.common.util.request.IpUtil;
import com.cabin.utils.dateUtil.DateUtil;
import com.dingtalk.api.request.OapiRobotSendRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author 伍六七
 * @date 2023/5/22 11:12
 * aop
 * 切面
 * 切点
 * 连接点--切点
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {


    private static final ThreadLocal<Long> treadLocal = new ThreadLocal<>();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 以 controller 包下定义的所有请求为切入点
     * 切点
     */

    @Pointcut("normalPointcutWeb() && !excludePointcutWeb()")
    public void allPointcutWeb() {
    }

    @Pointcut("execution(public * com.cabin.controller..*.*(..))")
    public void normalPointcutWeb() {
    }

    @Pointcut("execution(public *  com.cabin.controller.UploadController.uploadPost(..))")
    public void excludePointcutWeb() {
    }

    /**
     * 在切点之前织入
     * 利用aop来实现请求前后的用户信息的获取，及服务器接口相关信息
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("allPointcutWeb()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //断言,不符合条件会抛异常,测试使用,生产不建议,
//        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        StringBuilder Build = new StringBuilder();
        String remoteIp = IpUtil.getClientIpAddr(request);
        String header = request.getHeader("user-agent");
        UserAgentParser singleton = DeviceUtil.getSingleton();
        Capabilities capabilities = singleton.parse(header);
        Build.append("## user" + "\n");
        Build.append("> **remoteIp**:\n " + remoteIp + "\n\n");
        Build.append("> **browser**:\n " + capabilities.getBrowser() + "\n\n");
        Build.append("> **browserType**:\n " + capabilities.getBrowserType() + "\n\n");
        Build.append("> **browserMajorVersion**:\n " + capabilities.getBrowser() + "\n\n");
        Build.append("> **deviceType**:\n " + capabilities.getDeviceType() + "\n\n");
        Build.append("> **platform**:\n " + capabilities.getPlatform() + "\n\n");
        Build.append("> **platformVersion**:\n " + capabilities.getPlatformVersion() + "\n\n");
        Build.append("> **renderingEngineMaker**:\n " + capabilities.getValue(BrowsCapField.RENDERING_ENGINE_MAKER) + "\n\n");
        Build.append("## server" + "\n\n");
        Build.append("> **IP** :\n " + request.getRemoteAddr() + "\n\n");
//        Build.append("System name of the server:\n " + System.getProperty("os.name") + "\n\n");
//        Build.append("system version of the server:\n " + System.getProperty("os.version") + "\n\n");
        Build.append("> **Bits of server operating system**:\n " + System.getProperty("os.arch") + "\n\n");
        Build.append("> **URL**:\n " + request.getRequestURL().toString() + "\n\n");
        Build.append("> **HTTP Method**:\n " + request.getMethod() + "\n\n");
        Build.append("> **Class Method** : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "\n\n");
        try {
            Object requestParam = joinPoint.getArgs();
            Build.append("> **参数**:\n " + JSONObject.toJSONString(requestParam) + "\n\n");
        } catch (Exception e) {
            Build.append("> **参数打印失败，异常**:" + e.getMessage() + "\n\n");
        }
        long time = System.currentTimeMillis();
        treadLocal.set(time);
        redisTemplate.opsForValue().set("msg_dingding" + time, Build.toString());
//        Message message = new Message(request.getRemoteAddr(), request.getRequestURL().toString(), request.getMethod(), joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(), JSONObject.toJSONString(joinPoint.getArgs()));

    }

    /**
     * 在切点之后织入
     *
     * @throws Throwable
     */
    @After("allPointcutWeb()")
    public void doAfter() throws Throwable {
        log.info("=======================  End  ======================");
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("allPointcutWeb()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
//        logger.info("返回值 : {}", JSONObject.toJSONString(result));
        long need = System.currentTimeMillis() - startTime;
        // 执行耗时
//        logger.info("耗时 :\n {} ms", need);
        Long time = treadLocal.get();
        treadLocal.remove();
        String members = redisTemplate.opsForValue().get("msg_dingding" + time);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(members);
        stringBuilder.append("> **耗时** :\n" + need + "ms" + "\n\n");
        stringBuilder.append("> **时间** :\n" + DateUtil.getNowDateTimeStr());
        redisTemplate.delete("msg_dingding" + time);
        //第一个参数是交换机,第二个参数是你要传的key,第三个参数是消息
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("API监控通知");
        markdown.setText(stringBuilder.toString());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_CABIN_INFORM, MQRoutingKeyEnum.SEND_CABIN_INFORM_DINGDING.getRoutingKey(), JSON.toJSONString(markdown));
        return result;
    }

}
