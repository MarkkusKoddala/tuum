package com.example.tuum.service;

import com.example.tuum.entity.Account;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSenderService {
    private static final String ROUTING_KEY = "account.new";

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAccountCreated(Account account) {
        rabbitTemplate.convertAndSend(ROUTING_KEY, account);
    }
}

