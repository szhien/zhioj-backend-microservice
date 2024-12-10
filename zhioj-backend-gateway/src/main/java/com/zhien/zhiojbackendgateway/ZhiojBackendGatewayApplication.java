package com.zhien.zhiojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 禁用数据源
@EnableDiscoveryClient
public class ZhiojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhiojBackendGatewayApplication.class, args);
    }

}
