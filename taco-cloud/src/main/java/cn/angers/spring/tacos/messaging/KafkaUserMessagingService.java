package cn.angers.spring.tacos.messaging;

import cn.angers.spring.tacos.User;
import cn.angers.spring.tacos.domain.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

/**
 * @author : liuanglin
 * @date : 2022/6/14 19:21
 * @description : 订单消息服务- kafka 实现
 */
@Service
public class KafkaUserMessagingService implements UserMessagingService{

    @Autowired
    KafkaTemplate<String, String> template;

    private static final String USER_TOPIC = "tacocloud.user.topic";

    @Override
    public void sendUser(User user) {
        IntStream.range(0,10)
            .forEach(i -> template.send(USER_TOPIC,user.toString()));
    }
}
