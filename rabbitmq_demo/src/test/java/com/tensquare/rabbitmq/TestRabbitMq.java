package com.tensquare.rabbitmq;

import com.tensquare.RabbitmqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class TestRabbitMq {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    @Test
    public void testSendMessage(){
//        rabbitTemplate.convertAndSend("kangTopic","lian.hong","I love Spring");
        rabbitTemplate.convertAndSend("hong","","I love Spring");
    }
    @Test
    public void test1(){
//        rabbitTemplate.convertAndSend("kangTopic","lian.hong","I love Spring");
        rabbitTemplate.convertAndSend("Andy","I love Spring");
    }
    @Test
    public void test2(){
//        rabbitTemplate.convertAndSend("kangTopic","lian.hong","I love Spring");
        rabbitTemplate.convertAndSend("kelly","I love Spring");
    }
    @Test
    public void test3(){
//        rabbitTemplate.convertAndSend("kangTopic","lian.hong","I love Spring");
        rabbitTemplate.convertAndSend("Spring","I love Spring");
    }

}
