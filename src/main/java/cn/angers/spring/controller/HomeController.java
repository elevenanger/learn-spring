package cn.angers.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author : liuanglin
 * @date : 2022/6/7 17:59
 * @description : 首页 Controller
 * spring web 框架 Spring MVC
 * 其中最核心的概念 C - Controller
 * 处理请求并响应数据
 * spring 组件扫描会扫描 @Controller 注解声明的类
 * 为 HomeController 创建实例作为 Spring 应用上下文中的 bean
 * 纯粹的视图 Controller 可以在配置类中定义 addViewController 实现
 * 详见 WebConfig.java
 */
@Controller
public class HomeController {

    /**
     * @GetMapping 注解标识如果一个 HTTP GET 请求根路径 /
     * home() 将会处理该请求
     */
    @GetMapping("/")
    public String home() {
        /*
        return 对应的视图（模板名称）
         */
        return "home";
    }
}
