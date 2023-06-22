package com.cabin.common.aop;

import com.alibaba.fastjson2.JSONObject;
import com.blueconic.browscap.BrowsCapField;
import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.UserAgentParser;
import com.cabin.common.config.dingding.DingTalkHelper;
import com.cabin.common.util.request.DeviceUtil;
import com.cabin.common.util.request.IpUtil;
import com.cabin.utils.dateUtil.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@Component
public class WebLogAspect {


    private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    private static final ThreadLocal<Long> treadLocal = new ThreadLocal<>();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 以 controller 包下定义的所有请求为切入点
     */
    @Pointcut("execution(public * com.cabin.controller..*.*(..))")
    public void webLog() {
    }

    /**
     * 在切点之前织入
     * 利用aop来实现请求前后的用户信息的获取，及服务器接口相关信息
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //断言,不符合条件会抛异常,测试使用,生产不建议,
//        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        StringBuilder Build = new StringBuilder();
        String remoteIp = IpUtil.getClientIpAddr(request);
        String header = request.getHeader("user-agent");
        UserAgentParser singleton = DeviceUtil.getSingleton();
        Capabilities capabilities = singleton.parse(header);
        Build.append("----- user ------" + "\n");
        Build.append("remoteIp  : " + remoteIp + "\n");
        Build.append("browser  : " + capabilities.getBrowser() + "\n");
        Build.append("browserType  : " + capabilities.getBrowserType() + "\n");
        Build.append("browserMajorVersion  : " + capabilities.getBrowser() + "\n");
        Build.append("deviceType  : " + capabilities.getDeviceType() + "\n");
        Build.append("platform  : " + capabilities.getPlatform() + "\n");
        Build.append("platformVersion  : " + capabilities.getPlatformVersion() + "\n");
        Build.append("renderingEngineMaker  : " + capabilities.getValue(BrowsCapField.RENDERING_ENGINE_MAKER) + "\n");
        Build.append("----- server ------" + "\n");
        Build.append("IP   : " + request.getRemoteAddr() + "\n\n");
//        Build.append("System name of the server  : " + System.getProperty("os.name") + "\n\n");
//        Build.append("system version of the server  : " + System.getProperty("os.version") + "\n\n");
        Build.append("Bits of server operating system  : " + System.getProperty("os.arch") + "\n\n");
        Build.append("URL  : " + request.getRequestURL().toString() + "\n");
        Build.append("HTTP Method  : " + request.getMethod() + "\n");
        Build.append("Class Method : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "\n\n");
        try {
            Object requestParam = joinPoint.getArgs();
            Build.append("参数  : " + JSONObject.toJSONString(requestParam) + "\n\n");
        } catch (Exception e) {
            Build.append("参数打印失败，异常:" + e.getMessage() + "\n\n");
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
    @After("webLog()")
    public void doAfter() throws Throwable {
        logger.info("=======================  End  ======================");
    }

    /**
     * 环绕
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 打印出参
//        logger.info("返回值 : {}", JSONObject.toJSONString(result));
        long need = System.currentTimeMillis() - startTime;
        // 执行耗时
//        logger.info("耗时   : {} ms", need);
        Long time = treadLocal.get();
        String members = redisTemplate.opsForValue().get("msg_dingding" + time);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(members);
        stringBuilder.append("耗时   :" + need + "ms" + "\n");
        stringBuilder.append("时间   :" + DateUtil.getNowDateTimeStr());
        redisTemplate.delete("msg_dingding" + time);
        DingTalkHelper.sendMessageByText(stringBuilder.toString(), null, true);
        return result;
    }

}
