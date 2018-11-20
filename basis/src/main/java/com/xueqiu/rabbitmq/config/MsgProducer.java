package com.xueqiu.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * Created by 郭根权 on 2018/11/21.
 * 消息生产者
 */
public class MsgProducer implements RabbitTemplate.ConfirmCallback{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {

    }
}
