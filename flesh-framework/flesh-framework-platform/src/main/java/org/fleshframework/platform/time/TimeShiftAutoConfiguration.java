/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fleshframework.platform.time;

import org.fleshframework.platform.time.impl.FleshTimeShiftService;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeShiftAutoConfiguration {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    Queue timeShiftQueue() {
        return new AnonymousQueue();
    }

    @Bean
    Exchange timeShiftExchange() {
        return new FanoutExchange("flesh.time-shift.fan", false, false);
    }

    @Bean
    Binding timeShiftBinding(Queue timeShiftQueue, FanoutExchange timeShiftExchange) {
        return BindingBuilder.bind(timeShiftQueue).to(timeShiftExchange);
    }

    @Bean
    SimpleMessageListenerContainer timeShiftListenerContainer(ConnectionFactory connectionFactory, Queue timeShiftQueue, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(timeShiftQueue.getName());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    TimeShiftService timeShiftService() {
        return new FleshTimeShiftService();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(TimeShiftService service) {
        return new MessageListenerAdapter(service, "receiveMessage");
    }
}
