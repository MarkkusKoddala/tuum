package com.example.tuum.service;

import com.example.tuum.entity.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSenderService {
    private static final String ROUTING_KEY = "account.insert";
    private static final String EXCHANGE_NAME = "account_exchange";
    private final ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSenderService(RabbitTemplate rabbitTemplate) {
        this.objectMapper = new ObjectMapper();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAccountCreated(Account account) {
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, objectMapper.writeValueAsString(account));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

