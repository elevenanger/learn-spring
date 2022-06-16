package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * @author : liuanglin
 * @date : 2022/6/16 14:48
 * @description : Mono 类型 publisher  测试
 */
class MonoReactiveStreamTests {

    @Test
    void createMono() {
        /*
        使用 Mono.just() 创建一个 Mono 类型 publisher
        publisher 中的数据为 only one 字符串
         */
        Mono<String> name = Mono.just("only one");
        /*
        操作 publisher 中的数据
        operation
        输出结果 ONLY ONE
         */
        name.map(String::toUpperCase)
            /*
            subscriber 订阅者 订阅操作执行的结果
            进行逻辑处理
             */
            .subscribe(System.out::println);
    }
}
