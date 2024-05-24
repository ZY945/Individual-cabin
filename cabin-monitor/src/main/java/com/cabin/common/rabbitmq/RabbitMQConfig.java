package com.cabin.common.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    ///////////////////////////////////queue///////////////////////////////////
    public static final String QUEUE_MONITOR_EMAIL = "queue_monitor_email";
    public static final String QUEUE_INFORM_SMS = "queue_monitor_sms";
    public static final String QUEUE_INFORM_WX = "queue_monitor_wx";
    public static final String QUEUE_MONITOR_DingDing = "queue_monitor_dingding";

    ///////////////////////////////////exchange///////////////////////////////////
    public static final String EXCHANGE_CABIN_MONITOR = "cabin.monitor";

    ///////////////////////////////////routingKey///////////////////////////////////
    public static final String ROUTING_KEY_EMAIL = "monitor.#.email.#";
    public static final String ROUTING_KEY_SMS = "monitor.#.sms.#";
    public static final String ROUTING_KEY_WX = "monitor.#.wx.#";
    public static final String ROUTINGKEY_DingDing = "monitor.#.dingding.#";


    //声明交换机
    @Bean(EXCHANGE_CABIN_MONITOR)
    public Exchange EXCHANGE_TOPICS_INFORM() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_CABIN_MONITOR).durable(true).build();
    }


    //声明QUEUE_MONITOR_EMAIL队列
    @Bean(QUEUE_MONITOR_EMAIL)
    public Queue QUEUE_MONITOR_EMAIL() {
        return new Queue(QUEUE_MONITOR_EMAIL);
    }

    //声明QUEUE_INFORM_SMS队列
    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS() {
        return new Queue(QUEUE_INFORM_SMS);
    }

    //声明QUEUE_INFORM_WX队列
    @Bean(QUEUE_INFORM_WX)
    public Queue QUEUE_INFORM_WX() {
        return new Queue(QUEUE_INFORM_WX);
    }

    @Bean(QUEUE_MONITOR_DingDing)
    public Queue QUEUE_MONITOR_DingDing() {
        return new Queue(QUEUE_MONITOR_DingDing);
    }


    //ROUTING_KEY_EMAIL队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_QUEUE_MONITOR_EMAIL(@Qualifier(QUEUE_MONITOR_EMAIL) Queue queue,
                                               @Qualifier(EXCHANGE_CABIN_MONITOR) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
    }

    //ROUTING_KEY_SMS队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
                                            @Qualifier(EXCHANGE_CABIN_MONITOR) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SMS).noargs();
    }

    //ROUTING_KEY_WX队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_QUEUE_INFORM_WX(@Qualifier(QUEUE_INFORM_WX) Queue queue,
                                           @Qualifier(EXCHANGE_CABIN_MONITOR) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_WX).noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_DINGDING(@Qualifier(QUEUE_MONITOR_DingDing) Queue queue,
                                                 @Qualifier(EXCHANGE_CABIN_MONITOR) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_DingDing).noargs();
    }

}
