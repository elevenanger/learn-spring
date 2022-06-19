package reactor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.stream.IntStream;

/**
 * @author : liuanglin
 * @date : 2022/6/19 09:07
 * @description : 多个 Reactive 对象之间的逻辑运算
 */
class ReactiveLogicOperationTests {

    Flux<Integer> flux;
    Mono<Boolean> hasMono;

    @BeforeEach
    void preparation() {
        flux = Flux.fromStream(IntStream.range(0,3).boxed());
    }

    /**
     * all() 操作测试 flux 中的所有元素都匹配指定的表达式
     */
    @Test
    void all() {
        hasMono = flux.all(i -> i > -1); StepVerifier.create(hasMono)
            .expectNext(true)
            .verifyComplete();
    }

    /**
     * any() 操作匹配 flux 中至少有一个元素符合指定的表达式
     */
    @Test
    void any() {
        hasMono = flux.any( i -> i==2);
        StepVerifier.create(hasMono)
            .expectNext(true)
            .verifyComplete();
    }
}
