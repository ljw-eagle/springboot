package com.redis.demo.controller.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * 生产者（queue 和 topic）
 * Queue：队列，目标类型之一，和PTP结合(点对点)
 * Topic：主题，目标类型之一，和Pub/Sub结合 (Publish/Subscribe，即发布-订阅)模型，
 */
@RestController
public class ProducerController
{
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @PostMapping("/queue/test")
    public String sendQueue(@RequestBody String str) {
        this.sendMessage(this.queue, str);
        return "success";
    }

    @PostMapping("/topic/test")
    public String sendTopic(@RequestBody String str) {
        this.sendMessage(this.topic, str);
        return "success";
    }

    // 发送消息，destination是发送到的队列，message是待发送的消息
    private void sendMessage(Destination destination, final String message){
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
