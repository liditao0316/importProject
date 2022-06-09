package com.example.rabbitmq;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 描述：发送秒杀订单
     * @param message
     */
    public void sendSeckillMessage(String message){
        rabbitTemplate.convertAndSend("seckillExchange","seckill.message",message);
    }
}
