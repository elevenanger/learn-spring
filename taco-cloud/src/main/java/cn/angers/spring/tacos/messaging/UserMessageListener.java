package cn.angers.spring.tacos.messaging;

import cn.angers.spring.tacos.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author : liuanglin
 * @date : 2022/6/14 19:36
 * @description : 消费用户消息
 */
@Component
@Slf4j
public class UserMessageListener {

    @KafkaListener(topics = "tacocloud.user.topic")
    public void handle(User user, ConsumerRecord<String,User> record){
        log.info("收到用户注册信息 {} ", user);
        StringBuilder recordBuilder = new StringBuilder("记录详情：\n\t");
        recordBuilder
            .append("topic : ")
            .append(record.topic())
            .append(", 时间戳 ：")
            .append(record.timestamp())
            .append(", 分区")
            .append(record.partition())
            .append(", offset : ")
            .append(record.offset());
        log.info(recordBuilder.toString());
    }
}
