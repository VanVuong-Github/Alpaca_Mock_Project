package com.devcamp.Project;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    public static final String TOPIC_EXCHANGE_NAME = "spring-boot-topic-exchange";

    public static final String TOPIC_QUEUE_1_NAME = "topic.create";

    public static final String TOPIC_QUEUE_2_NAME = "topic.update";

    public static final String TOPIC_QUEUE_3_NAME = "topic.delete";

    @Bean
    public Declarables topicBindings() {
        Queue topicQueue1 = new Queue(TOPIC_QUEUE_1_NAME, false);
        Queue topicQueue2 = new Queue(TOPIC_QUEUE_2_NAME, false);
        Queue topicQueue3 = new Queue(TOPIC_QUEUE_3_NAME, false);
        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);
        return new Declarables(
                topicQueue1,
                topicQueue2,
                topicQueue3,
                topicExchange,
                bind(topicQueue1).to(topicExchange).with(TOPIC_QUEUE_1_NAME),
                bind(topicQueue2).to(topicExchange).with(TOPIC_QUEUE_2_NAME),
                bind(topicQueue3).to(topicExchange).with(TOPIC_QUEUE_3_NAME)

        );
    }

    @RabbitListener(queues = {TOPIC_QUEUE_1_NAME})
    public void receiveMessageTopic1( ) {
        System.out.println(String.format("[%s] [%s] Received message: %s", TOPIC_EXCHANGE_NAME, TOPIC_QUEUE_1_NAME, "Created!"));
    }

    @RabbitListener(queues = {TOPIC_QUEUE_2_NAME})
    public void receiveMessageTopic2( ) {
        System.out.println(String.format("[%s] [%s] Received message: %s", TOPIC_EXCHANGE_NAME, TOPIC_QUEUE_2_NAME, "Updated!"));
    }

    @RabbitListener(queues = {TOPIC_QUEUE_3_NAME})
    public void receiveMessageTopic3( ) {
        System.out.println(String.format("[%s] [%s] Received message: %s", TOPIC_EXCHANGE_NAME, TOPIC_QUEUE_3_NAME, "Deleted!"));
    }


}
