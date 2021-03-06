package cn.angers.spring.tacos.controller;

import cn.angers.spring.tacos.User;
import cn.angers.spring.tacos.data.OrderRepository;
import cn.angers.spring.tacos.domain.TacoOrder;
import cn.angers.spring.tacos.web.OrderProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author : liuanglin
 * @date : 2022/6/8 14:15
 * @description : 订单信息 Controller
 */
@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {

    @Autowired
    private OrderProperties orderProperties;
    @Autowired
    private OrderRepository repository;

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    /**
     * 接收视图表单提交的订单数据
     * 完成订单
     * @param order 订单数据
     * @param status 会话状态
     * @return 订单完成 重定向
     */
    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                                SessionStatus status,
                                /*
                                处理订单信息
                                需要校验订单与提交订单的用户是否匹配
                                校验用户的身份信息
                                @AuthenticationPrincipal 注解 User 对象作为身份认证的主体
                                */
                                @AuthenticationPrincipal User user){
        if (errors.hasErrors()) return "orderForm";
        log.info("Order Submitted : {} " , order);
        order.setUser(user);
        // 存储用户提交的数据
        repository.save(order);
        /*
        订单完成
        会话结束
        清除会话信息
         */
        status.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(
        @AuthenticationPrincipal User user, Model model){
        // 分页条件
        Pageable pageable = PageRequest.of(0, orderProperties.getPageSize());
        model.addAttribute("orders",
            repository.findByUserOrderByCreatedDateDesc(user,pageable));
        return "orderList";
    }
}
