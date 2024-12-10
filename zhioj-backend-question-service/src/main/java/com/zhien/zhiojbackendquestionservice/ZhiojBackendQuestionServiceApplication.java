package com.zhien.zhiojbackendquestionservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zhien.zhiojbackendquestionservice.mapper")
@EnableScheduling
@ComponentScan("com.zhien")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ZhiojBackendQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhiojBackendQuestionServiceApplication.class, args);
    }

}
