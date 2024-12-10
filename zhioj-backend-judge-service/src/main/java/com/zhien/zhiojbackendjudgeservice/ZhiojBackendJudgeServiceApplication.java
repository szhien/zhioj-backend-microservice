package com.zhien.zhiojbackendjudgeservice;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.zhien")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Slf4j
public class ZhiojBackendJudgeServiceApplication {
    /**
     * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
     */
    public static void doInitMq() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = "do_judge_exchange";
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 创建队列，随机分配一个队列名称
            String queueName = "do_judge_queue";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, "my_routing_key");
            log.info("创建队列成功");
        } catch (Exception e) {
            log.error("创建队列失败", e);
        }
    }

    public static void main(String[] args) {
        doInitMq();
        SpringApplication.run(ZhiojBackendJudgeServiceApplication.class, args);
    }

}
