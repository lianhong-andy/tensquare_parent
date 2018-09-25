package com.tensquare.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "Spring")
public class MyRabbitListener3 {
    @RabbitHandler
    public void showMessage(String message){
        System.out.println("Spring接收到消息："+message);
    }
}
