package com.alpaca.Alpaca_Mock_Project;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    public static final String TOPIC_EXCHANGE_NAME = "eventExchange";
    public static final String CUSTOMER_CREATED_QUEUE = "customer.created";
    public static final String CUSTOMER_UPDATED_QUEUE = "customer.updated";
    public static final String CUSTOMER_DELETED_QUEUE = "customer.deleted";

    @Bean
    public Declarables topicBindings(){
        Queue customerCreatedQueue = new Queue(CUSTOMER_CREATED_QUEUE, false);
        Queue customerUpdatedQueue = new Queue(CUSTOMER_UPDATED_QUEUE, false);
        Queue customerDeletedQueue = new Queue(CUSTOMER_DELETED_QUEUE, false);
        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
        return new Declarables(customerCreatedQueue, customerUpdatedQueue, customerDeletedQueue, topicExchange,
                BindingBuilder.bind(customerCreatedQueue).to(topicExchange).with(CUSTOMER_CREATED_QUEUE),
                BindingBuilder.bind(customerUpdatedQueue).to(topicExchange).with(CUSTOMER_UPDATED_QUEUE),
                BindingBuilder.bind(customerDeletedQueue).to(topicExchange).with(CUSTOMER_DELETED_QUEUE));
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
