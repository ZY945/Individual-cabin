package com.cabin.gateway.service;

import com.cabin.utils.commonUtil.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/8/17 22:12
 */
@Service
@Slf4j
public class AuthServiceImpl {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    static List<String> blackList = Arrays.asList("123", "456");

    /**
     * 黑名单
     *
     * @return 为null表示非黑名单
     */
    public Mono<Void> blackLoginLogic(ServerWebExchange exchange) {
        // 用户id-----可以是个黑名单的标志,也可以解析token后查数据库
        String id = exchange.getRequest().getHeaders().getFirst("id");
        // blackList可以做持久化
        if (blackList.contains(id)) {
            // 如果是黑名单就直接返回，不再往目标服务器转发
            log.info("黑名单拦截成功");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return null;
    }

    /**
     * 登录逻辑 <br/>
     * 没有cookie返回401
     * 有cookie但是不对返回403
     *
     * @return 为null表示登录成功
     */
    public Mono<Void> loginLogic(ServerWebExchange exchange) {
        // 在这里编写登录检测逻辑
        // 如果登录有效，调用chain.filter(exchange)继续处理请求
        // 如果登录无效，可以返回未授权的响应或重定向到登录页面
        String token = null;
        HttpCookie tokenCookie = exchange.getRequest().getCookies().getFirst("token");
        if (tokenCookie == null) {
            // 如果没有登录则重定向都登录页面
            log.info("cookie为null");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        token = tokenCookie.getValue();
        // 功能1.拿到token判断是否已经登录
        if (!isLogin(token)) {
            // 如果没有登录则重定向都登录页面
            log.info("cookie错误");
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        // 如果判断成功了放行
        log.info("登录成功");
        return null;
    }

    /**
     * 验证是否是游客或者用户身份
     *
     * @param token
     * @return 返回是true表示验证成功
     */
    public boolean isLogin(String token) {
        String user = redisTemplate.opsForValue().get("user:token:" + token);
        if (!StringUtil.isNullOrEmpty(user)) {
            log.info("是用户");
            return true;
        }
        String guest = redisTemplate.opsForValue().get("guest:token:" + token);
        if (!StringUtil.isNullOrEmpty(guest)) {
            log.info("是游客");
            return true;
        }
        return false;
    }
}
