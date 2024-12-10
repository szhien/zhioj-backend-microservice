package com.zhien.zhiojbackendquestionservice.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Zhien
 * @version 1.0
 * @name MyMessageProducer
 * @description 消息队列消息生产者：提交题目id发送到队列中
 * @createDate 2024/12/07 10:54
 */
@Component
@Slf4j
public class MyMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息到指定交换机和路由键
     *
     * @param exchange   交换机
     * @param routingKey 路由键
     * @param message    消息
     */
    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}
