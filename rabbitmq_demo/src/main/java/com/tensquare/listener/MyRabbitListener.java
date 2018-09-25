package com.tensquare.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "Andy")
public class MyRabbitListener {
    @RabbitHandler
    public void showMessage(String message){
        System.out.println("Andy接收到消息："+message);
    }
}
