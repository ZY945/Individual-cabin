//package com.cabin.gateway.config;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class MyGlobalFilter implements GlobalFilter, Ordered {
//
//    private static final String START_TIME = "startTime";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        // 下面3行代码在前过滤器pre filter执行
//        String url = exchange.getRequest().getURI().getPath();
//        System.out.println("ip来源: " + "这里从nginx获取ip握手时的ip");
//        System.out.println("请求地址：" + url);
//        System.out.println("入参：" + exchange.getRequest().getQueryParams().toString());
//        // exchange的getAttributes可以存放信息
//        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
//
//        // chain.filter里面的逻辑相当于后过滤器post filter
//        return chain.filter(exchange).then(
//                Mono.fromRunnable(() -> {
//                    Long startTime = exchange.getAttribute(START_TIME);
//                    if (startTime != null) {
//                        System.out.println(url + "耗时：" +
//                                (System.currentTimeMillis() - startTime) + "ms");
//                    }
//                })
//        );
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}