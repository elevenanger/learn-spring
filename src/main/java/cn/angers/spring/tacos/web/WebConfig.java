package cn.angers.spring.tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : liuanglin
 * @date : 2022/6/8 15:32
 * @description : 视图 Controller
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 添加视图 Controller
     * 视图 Controller ： 将请求转发到视图之外不做任何处理的 Controller
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /*
        替代 HomeController
         */
        registry.addViewController("/").setViewName("home");
    }
}
