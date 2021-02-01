package com.redis.demo.common.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Queue模式的消费者
 */
@Component
public class QueueConsumerListener
{
    /**
     * 其中destination指定监控的消息队列名称为“spring.activemq.queue-name”。
     * 当队列spring.activemq.queue-name中有消息发送时会触发此方法的执行，text为消息内容。
     * @param message
     * 这里分别实例化了基于队列和订阅的工厂类。然后分别在对应的消费者方法上添加containerFactory属性。
     */
    //queue模式的消费者
    @JmsListener(destination="${spring.activemq.queue-name}", containerFactory="queueListener")
    public void readActiveQueue(String message) {
        System.out.println("queue接受到：" + message);
    }
}
