package com.cabin.gateway.config;

import com.cabin.gateway.service.AuthServiceImpl;
import com.cabin.gateway.util.ApplicationContextUtil;
import com.cabin.utils.jacksonUtil.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@DependsOn("ApplicationContextUtil")
@Slf4j
public class AuthFilter implements GatewayFilter, Ordered {
    private final AuthServiceImpl authService = (AuthServiceImpl) ApplicationContextUtil.getBean("authServiceImpl");
    // 黑名单列表，也可以放在缓存数据库里
    private static final String START_TIME = "startTime";
    private static final String app = "/login";
    private static final String ADMIN = "admin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Mono<Void> noLogin = authService.loginLogic(exchange);
        log.info("登录验证结束");
        if (noLogin != null) return noLogin;
        Mono<Void> blackUser = authService.blackLoginLogic(exchange);
        log.info("黑名单验证结束");
        if (blackUser != null) return blackUser;
        // 下面3行代码在前过滤器pre filter执行
        String url = exchange.getRequest().getURI().getPath();
        System.out.println("ip来源: " + "这里从nginx获取ip握手时的ip");
        System.out.println("请求地址：" + url);
        System.out.println("入参：" + exchange.getRequest().getQueryParams().toString());
        // exchange的getAttributes可以存放信息
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());

        // chain.filter里面的逻辑相当于后过滤器post filter
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(START_TIME);
                    if (startTime != null) {
                        System.out.println(url + "耗时：" +
                                (System.currentTimeMillis() - startTime) + "ms");
                    }
                })
        );
        // 如果放行不需要日志
//        return chain.filter(exchange);
    }


    /**
     * 拦截并返回自定义的json字符串
     */
    private <T> Mono<Void> denyAccess(ServerWebExchange exchange, T t) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        //这里在返回头添加编码，否则中文会乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] bytes = JacksonUtils.convertValue(t, byte[].class);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}