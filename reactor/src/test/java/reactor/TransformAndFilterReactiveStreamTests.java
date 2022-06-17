package reactor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.IntStream;

/**
 * @author : liuanglin
 * @date : 2022/6/17 19:13
 * @description : 转换与过滤 Reactive Stream
 */
class TransformAndFilterReactiveStreamTests {

    Flux<Integer> integerFlux1;
    Flux<Integer> integerFlux2;

    @BeforeEach
    void prepare() {
        integerFlux1 = Flux.fromStream(IntStream.range(0,2).boxed());
        integerFlux2 = Flux.fromStream(IntStream.range(2,4).boxed());
    }

    /**
     * 略过 Flux 中的元素
     */
    @Test
    void skipElement() {
        Flux<Integer> skipFlux =
            Flux
            .merge(integerFlux1,integerFlux2)
                // 跳过前三个元素然后将剩下的元素放入 flux 中
                .skip(3);
        StepVerifier
            .create(skipFlux)
            .expectNext(3)
            .verifyComplete();
    }

}
