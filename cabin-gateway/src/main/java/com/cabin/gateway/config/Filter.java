package com.cabin.gateway.config;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/8/12 20:42
 */
public class Filter {

    static List<String> blackList = Arrays.asList("123", "456");

    /**
     * 黑名单
     *
     * @return 为null表示非黑名单
     */
    public static Mono<Void> blackLoginLogic(ServerWebExchange exchange) {
        // 用户id
        String id = exchange.getRequest().getHeaders().getFirst("id");
        // blackList可以做持久化
        if (blackList.contains(id)) {
            // 如果是黑名单就直接返回，不再往目标服务器转发
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return null;
    }

    /**
     * 登录逻辑 <br/>
     *
     * @return 为null表示登录成功
     */
    public static Mono<Void> loginLogic(ServerWebExchange exchange) {
        // 在这里编写登录检测逻辑
        // 如果登录有效，调用chain.filter(exchange)继续处理请求
        // 如果登录无效，可以返回未授权的响应或重定向到登录页面
        String token = null;
        HttpCookie tokenCookie = exchange.getRequest().getCookies().getFirst("token");
        if (tokenCookie != null) {
            token = tokenCookie.getValue();
        }
        // 功能1.拿到token判断是否已经登录
        if (!isLogin(token)) {
            // 如果没有登录则重定向都登录页面
            exchange.getResponse().setStatusCode(HttpStatus.FOUND);
            exchange.getResponse().getHeaders().set("Location", "/login");
            return exchange.getResponse().setComplete();
        }
        // 如果判断成功了放行
        System.out.println("登录成功");
        return null;
    }

    public static boolean isLogin(String token) {
        return true;
    }
}
