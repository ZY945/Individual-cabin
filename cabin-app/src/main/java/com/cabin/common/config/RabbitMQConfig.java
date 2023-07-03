package com.cabin.common.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    ///////////////////////////////////queue///////////////////////////////////
    public static final String QUEUE_ALARM_DingDing = "queue_alarm_dingding";
    public static final String QUEUE_ALARM_EMAIL = "queue_alarm_email";
    public static final String QUEUE_INFORM_API_DingDing = "queue_inform_api_dingding";

    ///////////////////////////////////exchange///////////////////////////////////
    public static final String EXCHANGE_CABIN_ALARM = "cabin.alarm";
    public static final String EXCHANGE_CABIN_INFORM = "cabin.inform";

    ///////////////////////////////////routingKey///////////////////////////////////

    public static final String ROUTINGKEY_ALARM_DingDing = "alarm.#.dingding.#";
    public static final String ROUTINGKEY_ALARM_EMAIL = "alarm.#.email.#";

    public static final String ROUTINGKEY_INFORM_API_DingDing = "inform.api.#.dingding.#";


    ///////////////////////////////////交换机,设置自动创建持久化///////////////////////////////////
    //声明交换机,会在消息队列创建
    @Bean(EXCHANGE_CABIN_ALARM)
    public Exchange EXCHANGE_CABIN_ALARM() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_CABIN_ALARM).durable(true).build();
    }

    //声明交换机,会在消息队列创建
    @Bean(EXCHANGE_CABIN_INFORM)
    public Exchange EXCHANGE_CABIN_INFORM() {
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_CABIN_INFORM).durable(true).build();
    }

    ///////////////////////////////////队列,设置自动创建持久化///////////////////////////////////
    // 报警
    @Bean(QUEUE_ALARM_DingDing)
    public Queue QUEUE_ALARM_DingDing() {
        return new Queue(QUEUE_ALARM_DingDing);
    }

    @Bean(QUEUE_ALARM_EMAIL)
    public Queue QUEUE_ALARM_EMAIL() {
        return new Queue(QUEUE_ALARM_EMAIL);
    }

    // 监控
    @Bean(QUEUE_INFORM_API_DingDing)
    public Queue QUEUE_INFORM_API_DingDing() {
        return new Queue(QUEUE_INFORM_API_DingDing);
    }


    ///////////////////////////////////绑定交换机和队列///////////////////////////////////
    // 报警
    @Bean
    public Binding BINDING_QUEUE_ALARM_DINGDING(@Qualifier(QUEUE_ALARM_DingDing) Queue queue,
                                                @Qualifier(EXCHANGE_CABIN_ALARM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_ALARM_DingDing).noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_ALARM_EMAIL(@Qualifier(QUEUE_ALARM_EMAIL) Queue queue,
                                             @Qualifier(EXCHANGE_CABIN_ALARM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_ALARM_EMAIL).noargs();
    }

    // 监控
    @Bean
    public Binding BINDING_QUEUE_INFORM_DINGDING(@Qualifier(QUEUE_INFORM_API_DingDing) Queue queue,
                                                 @Qualifier(EXCHANGE_CABIN_INFORM) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_INFORM_API_DingDing).noargs();
    }

}