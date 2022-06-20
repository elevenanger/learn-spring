package flux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author : liuanglin
 * @date : 2022/6/20 14:10
 * @description : 使用函数编程模型定义响应式 API
 */
@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<?> helloRouterFunction() {
        /*
        RouterFunctions 声明如何匹配对应的请求路由至 Handler 代码
         */
        return RouterFunctions
                /*
                route() 方法包括两个参数
                RequestPredicates 声明需要处理的请求类型
                以及处理请求的函数
                处理请求的函数接收 ServerRequest 对象作为参数
                返回 ServerResponse 对象
                 */
                .route(RequestPredicates.GET("/hello"),
                    /*
                    ServerResponse 服务器响应内容
                    包括响应头和响应体的内容
                     */
                    request -> ServerResponse.ok().body(
                        Mono.just("Hello Reactive"),String.class))
                // andRoute() 方法声明另一个路由请求映射
                .andRoute(RequestPredicates.GET("/bye"),
                    request -> ServerResponse.ok().body(
                        Mono.just("Bye Reactive"),String.class));
    }

}
