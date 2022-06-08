package cn.angers.spring.tacos.controller;

import cn.angers.spring.tacos.domain.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

/**
 * @author : liuanglin
 * @date : 2022/6/8 14:15
 * @description : 订单信息 Controller
 */
@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

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
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus status){
        if (errors.hasErrors()) return "orderForm";
        log.info("Order Submitted : {} " , order);
        /*
        订单完成
        会话结束
        清除会话信息
         */
        status.setComplete();
        return "redirect:/";
    }
}
