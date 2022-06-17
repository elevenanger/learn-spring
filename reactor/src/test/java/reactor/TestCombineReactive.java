package reactor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.stream.IntStream;

/**
 * @author : liuanglin
 * @date : 2022/6/17 14:09
 * @description : 组合 Reactive 类型
 */
class TestCombineReactive {

    Flux<Integer> integerFlux1;
    Flux<Integer> integerFlux2;

    Flux<FluxHelper> helperFlux;

    @BeforeEach
    void fluxPrepare() {
        integerFlux1 = Flux.fromStream(IntStream.range(0,2).boxed());
        integerFlux2 = Flux.fromStream(IntStream.range(2,4).boxed());
        helperFlux = Flux.fromStream(IntStream.range(0,2).boxed().map(FluxHelper::new));
    }
    /**
     * flux1.mergeWith(flux2)
     * 合并 flux
     */
    @Test
    void mergeFlux() {
        Flux<Integer> mergedFlux = integerFlux1.mergeWith(integerFlux2);
        StepVerifier.create(mergedFlux)
            .expectNext(0)
            .expectNext(1)
            .expectNext(2)
            .expectNext(3)
            .verifyComplete();
    }

    /**
     * 使用 Flux.zip(f1,f2) 合并 Flux
     * 合并后的 flux 中的每个元素都是合并前的 flux 组成的元组数据
     */
    @Test
    void zipFluxes() {
        Flux<Tuple2<Integer,FluxHelper>> zippedFlux = Flux.zip(integerFlux1,helperFlux);
        StepVerifier.create(zippedFlux)
            .expectNextMatches(
                p -> p.getT1().equals(p.getT2().getI())
            ).expectNextMatches(
                p -> p.getT1().equals(p.getT2().getI())
            ).verifyComplete();
    }

    /**
     * 使用 zip 和并 Flux
     * 使用合并 Flux 的元组中的对象创建新的对象作为新 Flux 中的元素
     */
    @Test
    void zipFluxesToObj() {
        Flux<Integer> zippedFlux = Flux.zip(integerFlux1,helperFlux,
            (f1,f2) -> f1 + f2.getI());
        StepVerifier.create(zippedFlux)
            .expectNext(0)
            .expectNext(2)
            .verifyComplete();
    }

    /**
     * 选择最优先的 Reactive 对象
     */
    @Test
    void firstWithSignalFlux() {
        /*
        设置 flux 消费订阅延时
         */
        integerFlux1 = integerFlux1.delaySubscription(Duration.ofMillis(100));
        /*
        从两个 Flux 对象中选择首先消费到的 flux
         */
        Flux<Integer> firstFlux = Flux.firstWithSignal(integerFlux1,integerFlux2);
        StepVerifier.create(firstFlux)
            .expectNext(2)
            .expectNext(3)
            .verifyComplete();
    }

    /**
     * 选择最先产生元素的 Flux
     */
    @Test
    void firstWithProduceFlux() {
        /*
        延迟 Flux 中每个元素产生的速率
         */
        integerFlux1 = integerFlux1.delayElements(Duration.ofMillis(100));
        /*
        延迟 Flux 中第一个元素产生的速率
         */
        integerFlux2 = integerFlux2.delaySequence(Duration.ofMillis(50));
        /*
        选择最先产生 value 的 Flux
         */
        Flux<Integer> firstFlux = Flux.firstWithValue(integerFlux1,integerFlux2);
        StepVerifier.create(firstFlux)
            .expectNext(2)
            .expectNext(3)
            .verifyComplete();
    }
}
