package com.xueqiu.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by 郭根权 on 2018/11/21.
 * 消息生产者
 */
public class MsgProducer implements RabbitTemplate.ConfirmCallback{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;

    /**
     * 在此用@Autowired防止NPT
     * @param rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(String msg){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE_A,RabbitConfiguration.ROUTINGKEY_A,msg,correlationId);
    }

    public void sendAll(String msg){

    }
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("回调ID-------->{}",correlationData);
        if (ack){
            logger.info("消费成功");
        }else {
            logger.info("消费失败--------->{}",cause);
        }
    }
}
