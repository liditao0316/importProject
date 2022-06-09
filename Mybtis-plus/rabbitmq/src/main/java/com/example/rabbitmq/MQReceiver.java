package com.example.rabbitmq;



import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {


    /**
     * 描述：秒杀订单生成
     * @param message
     */
    @RabbitListener(queues = "seckillQueue")
    public void receive(String message){
        System.out.println(message);
    }

}
