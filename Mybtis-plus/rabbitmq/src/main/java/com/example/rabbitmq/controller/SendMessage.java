package com.example.rabbitmq.controller;

import com.example.rabbitmq.MQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SendMessage {

    @Autowired
    MQSender mqSender;

    @GetMapping("/send")
    public void send(){
        mqSender.sendSeckillMessage("123");
    }
}
