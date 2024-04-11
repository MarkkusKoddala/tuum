package com.example.tuum.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "account_exchange";
    public static final String INSERT_QUEUE = "account_insert_queue";
    public static final String UPDATE_QUEUE = "account_update_queue";

    @Bean
    Queue insertQueue() {
        return new Queue(INSERT_QUEUE, true);
    }

    @Bean
    Queue updateQueue() {
        return new Queue(UPDATE_QUEUE, true);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    Binding bindingInsert(Queue insertQueue, DirectExchange exchange) {
        return BindingBuilder.bind(insertQueue).to(exchange).with("account.insert");
    }

    @Bean
    Binding bindingUpdate(Queue updateQueue, DirectExchange exchange) {
        return BindingBuilder.bind(updateQueue).to(exchange).with("account.update");
    }
}

