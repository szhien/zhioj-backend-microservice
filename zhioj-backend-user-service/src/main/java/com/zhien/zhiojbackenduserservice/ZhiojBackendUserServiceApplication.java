package com.zhien.zhiojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zhien.zhiojbackenduserservice.mapper")
@EnableScheduling
@ComponentScan("com.zhien")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ZhiojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhiojBackendUserServiceApplication.class, args);
    }

}
