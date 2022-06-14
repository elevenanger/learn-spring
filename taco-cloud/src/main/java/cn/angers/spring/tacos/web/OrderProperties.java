package cn.angers.spring.tacos.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : liuanglin
 * @date : 2022/6/11 17:48
 * @description : order 配置类
 */
@Component
@ConfigurationProperties(prefix = "taco.orders")
@Data
public class OrderProperties {

    private int pageSize = 20;
}
