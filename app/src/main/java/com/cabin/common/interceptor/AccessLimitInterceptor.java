package com.cabin.common.interceptor;

import com.cabin.common.annotation.AccessLimit;
import com.cabin.common.util.request.IpUtil;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import com.cabin.utils.response.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


/**
 * @author 伍六七
 * @date 2023/6/24 12：54
 */
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            //方法上没有访问控制的注解，直接通过
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxLimit();
            String ip = IpUtil.getClientIpAddr(request);
            String method = request.getMethod();
            String requestURI = request.getRequestURI();

            String redisKey = ip + ":" + method + ":" + requestURI;
            Object redisResult = redisTemplate.opsForValue().get(redisKey);
            Integer count = JacksonUtils.convertValue(redisResult, Integer.class);
            if (count == null) {
                //在规定周期内第一次访问，存入redis
                redisTemplate.opsForValue().increment(redisKey, 1);
                redisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
            } else {
                if (count >= maxCount) {
                    //超出访问限制次数
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    Result<Object> result = Result.requestLimitFail(accessLimit.msg());
                    // ServletOutputStream out = response.getOutputStream();
                    // getWriter() has already been called for this response报错
                    // fastJson, siteMesh, urlWriter等插件，应该response.getWriter()改为response.getOutputStream()
                    //  Result<Object> result = Result.requestLimitFail(accessLimit.msg());
                    //  out.write(JacksonUtils.writeValueAsString(result).getBytes());
                    out.write(JacksonUtils.writeValueAsString(result));
                    out.flush();
                    out.close();
                    return false;
                } else {
                    //没超出访问限制次数
                    redisTemplate.opsForValue().increment(redisKey, 1);
                }
            }
        }
        return true;
    }
}