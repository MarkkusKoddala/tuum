package com.example.tuum;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQInitializer implements CommandLineRunner {

    private final AmqpAdmin amqpAdmin;

    public RabbitMQInitializer(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    public void run(String... args) throws Exception {
        Queue queue = new Queue("testQueue", false);
        amqpAdmin.declareQueue(queue);
        System.out.println("Declared a test queue to ensure RabbitMQ connection.");
    }
}

