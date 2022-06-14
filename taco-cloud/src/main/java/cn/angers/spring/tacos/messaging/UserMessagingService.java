package cn.angers.spring.tacos.messaging;

import cn.angers.spring.tacos.User;

/**
 * @author : liuanglin
 * @date : 2022/6/14 18:52
 * @description : 订单消息服务
 */
public interface UserMessagingService {
    void sendUser(User user);
}
