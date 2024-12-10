package com.zhien.zhiojbackendgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author Zhien
 * @version 1.0
 * @name GlobalAuthFilter
 * @description 网关全局权限过滤器
 * @createDate 2024/12/07 08:39
 */
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    /**
     * 过滤器的执行逻辑:过滤内部请求，禁止外部访问
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        //1.获取请求
        ServerHttpRequest request = exchange.getRequest();
        //2.获取请求路径
        String path = request.getURI().getPath();
        //3.判断是否为内部请求
        if (antPathMatcher.match("/**/inner/**", path)) {
            //4.内部请求，禁止访问，无权限
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            //5.添加响应信息
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("无权限访问".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }

        //todo 统一权限校验，通过 JWT 获取登录用户信息
        return chain.filter(exchange);
    }

    /**
     * 返回过滤器的执行顺序，数字越小，优先级越高
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
