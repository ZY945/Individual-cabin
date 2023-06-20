package com.cabin.oauth2.common.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    ///////////////////////////////////queue///////////////////////////////////
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String QUEUE_INFORM_WX = "queue_inform_wx";
    public static final String QUEUE_INFORM_DingDing = "queue_inform_dingding";

    ///////////////////////////////////exchange///////////////////////////////////
    public static final String EXCHANGE_CABIN_INFORM = "cabin.inform";

    ///////////////////////////////////routingKey///////////////////////////////////
    public static final String ROUTING_KEY_EMAIL = "inform.#.email.#";
    public static final String ROUTING_KEY_SMS = "inform.#.sms.#";
    public static final String ROUTING_KEY_WX = "inform.#.wx.#";
    public static final String ROUTINGKEY_DingDing = "inform.#.dingding.#";


    //声明交换机
    @Bean(EXCHANGE_CABIN_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_CABIN_INFORM).durable(true).build();
    }


    //声明QUEUE_INFORM_EMAIL队列
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL() {
        return new Queue(QUEUE_INFORM_EMAIL);
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

    @Bean(QUEUE_INFORM_DingDing)
    public Queue QUEUE_INFORM_DingDing() {
        return new Queue(QUEUE_INFORM_DingDing);
    }


    //ROUTING_KEY_EMAIL队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
                                              @Qualifier(EXCHANGE_CABIN_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_EMAIL).noargs();
    }

    //ROUTING_KEY_SMS队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_QUEUE_INFORM_SMS(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
                                            @Qualifier(EXCHANGE_CABIN_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SMS).noargs();
    }

    //ROUTING_KEY_WX队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_QUEUE_INFORM_WX(@Qualifier(QUEUE_INFORM_WX) Queue queue,
                                           @Qualifier(EXCHANGE_CABIN_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_WX).noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_DINGDING(@Qualifier(QUEUE_INFORM_DingDing) Queue queue,
                                                 @Qualifier(EXCHANGE_CABIN_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_DingDing).noargs();
    }

}
