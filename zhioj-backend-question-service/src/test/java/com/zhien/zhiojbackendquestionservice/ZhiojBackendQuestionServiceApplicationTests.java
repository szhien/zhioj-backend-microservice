package com.zhien.zhiojbackendquestionservice;

import com.zhien.zhiojbackendquestionservice.producer.MyMessageProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ZhiojBackendQuestionServiceApplicationTests {

    @Resource
    private MyMessageProducer myMessageProducer;

    @Test
    void sendMessage() {
        myMessageProducer.sendMessage("do_judge_exchange", "my_routing_key", "测试，你好！");
    }

}
