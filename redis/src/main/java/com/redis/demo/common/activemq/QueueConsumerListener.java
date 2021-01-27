package com.redis.demo.common.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Queue模式的消费者
 */
@Component
public class QueueConsumerListener
{
    //queue模式的消费者
    @JmsListener(destination="${spring.activemq.queue-name}", containerFactory="queueListener")
    public void readActiveQueue(String message) {
        System.out.println("queue接受到：" + message);
    }
}
