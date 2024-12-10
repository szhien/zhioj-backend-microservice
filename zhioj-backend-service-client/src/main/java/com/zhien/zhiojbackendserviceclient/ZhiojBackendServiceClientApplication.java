package com.zhien.zhiojbackendserviceclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Zhien
 * @version 1.0
 * @name ZhiojBackendServiceClientApplication
 * @description
 * @createDate 2024/12/04 16:05
 */

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.zhien")
@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ZhiojBackendServiceClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZhiojBackendServiceClientApplication.class, args);
    }
}
