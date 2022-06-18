package reactor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : liuanglin
 * @date : 2022/6/17 19:13
 * @description : 转换与过滤 Reactive Stream
 */
class TransformAndFilterReactiveStreamTests {

    Flux<Integer> integerFlux1;
    Flux<Integer> integerFlux2;
    Flux<String> greetingFlux ;

    @BeforeEach
    void prepare() {
        integerFlux1 = Flux.fromStream(IntStream.range(0,2).boxed());
        integerFlux2 = Flux.fromStream(IntStream.range(2,4).boxed());
        greetingFlux = Flux.fromArray("helloflux".split(""));
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

    /**
     * skip() 方法可以传入 Duration 对象作为参数
     * 略过指定时间的元素
     */
    @Test
    void skipDuration() {
        Flux<Integer> skipFlux =
            Flux
                .merge(integerFlux1,integerFlux2)
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));
        StepVerifier.create(skipFlux).expectNext(3).verifyComplete();
    }

    /**
     * take() 与 skip() 方法刚好相反
     * 传入数值作为参数
     * 获取 flux 中的前几个元素
     */
    @Test
    void takeElements() {
        Flux<Integer> takeElementsFlux =
            Flux
                .merge(integerFlux1,integerFlux2)
                .take(2);
        StepVerifier.create(takeElementsFlux)
            .expectNext(0)
            .expectNext(1)
            .verifyComplete();
    }

    /**
     * Duration 对象作为参数
     * 则获取这一段时间内产生的元素
     */
    @Test
    void takeForAWhile() {
        Flux<Integer> takeForAWhileFlux =
            Flux
                .merge(integerFlux1,integerFlux2)
                .delayElements(Duration.ofMillis(80))
                .take(Duration.ofMillis(100));
        StepVerifier.create(takeForAWhileFlux)
            .expectNext(0).verifyComplete();
    }

    /**
     * filter 对 reactive 类型对象元素按条件进行匹配过滤
     */
    @Test
    void filter() {
        integerFlux1 = integerFlux1.filter(integer -> integer == 0);
        StepVerifier.create(integerFlux1).expectNext(0).verifyComplete();
    }

    /**
     * distinct() 方法过滤掉 Reactive 类型对象中重复的信息
     */
    @Test
    void distinct() {
        greetingFlux = greetingFlux
            .distinct()
            .map(s -> s +=" ");
        greetingFlux.subscribe(System.out::print);
    }

    /**
     * map() 在元素发布前将特定函数作用于 Reactive 类型对象中的每个元素
     * map() 方法是同步执行的
     * 因为每个元素都是源 flux 发布的
     */
    @Test
    void mapReactive() {
        Flux<FluxHelper> mapFlux =
        integerFlux1
            .map(FluxHelper::new);
        StepVerifier.create(mapFlux)
            .expectNext(new FluxHelper(0))
            .expectNext(new FluxHelper(1))
            .verifyComplete();
    }

    /*
    使用 flatMap 异步执行操作
     */
    @Test
    void flatMap() {
        Flux<FluxHelper> flatMapFlux =
            integerFlux1
                .mergeWith(integerFlux2)
                // 基于 flux 中的每个元素创建 Mono 类型对象再转换成 flux 对象
                .flatMap(integer ->
                    Mono.just(integer)
                        // 将各个 Mono 对象中的元素映射为 FluxHelper 对象
                        .map(FluxHelper::new)
                        /*
                        声明每个 Mono 对象的订阅方式为 parallel()
                        多个原始 flux 中的元素可以异步执行
                         */
                        .subscribeOn(Schedulers.parallel()));
        List<FluxHelper> fluxHelpers =
            IntStream.range(0,4).boxed()
                .map(FluxHelper::new)
                .collect(Collectors.toList());
        StepVerifier.create(flatMapFlux)
            .expectNextMatches(fluxHelpers::contains)
            .expectNextMatches(fluxHelpers::contains)
            .expectNextMatches(fluxHelpers::contains)
            .expectNextMatches(fluxHelpers::contains)
            .verifyComplete();
    }

    /**
     * buffer() 操作将传入 Flux 的元素分组存放在指定长度列表的 Flux 中
     */
    @Test
    void buffer() {
        Flux<List<Integer>> bufferedFlux =
            integerFlux1.mergeWith(integerFlux2)
                .buffer(3);
        StepVerifier.create(bufferedFlux)
            .expectNext(Arrays.asList(0,1,2))
            .expectNext(Collections.singletonList(3))
            .verifyComplete();
    }

    @Test
    void bufferedAndFlatMap() {
        integerFlux1.mergeWith(integerFlux2)
            .buffer(2)
            .flatMap(integers ->
                Flux.fromIterable(integers)
                    .map(FluxHelper::new)
                    .subscribeOn(Schedulers.parallel())
                    .log())
            .subscribe();
    }

    /**
     * collectList() 生成一个包含 flux 中全部元素的 List
     * 以此创建 Mono 对象
     */
    @Test
    void collectAllToList() {
        Mono<List<Integer>> integers = integerFlux1.collectList();
        StepVerifier.create(integers)
            .expectNext(Arrays.asList(0,1))
            .verifyComplete();
    }

    /**
     * map() 生成一个包含 flux 中全部元素的 map
     * 以此创建 Mono 对象
     */
    @Test
    void collectToMap() {
        Mono<Map<Integer,FluxHelper>> mapMono =
            integerFlux1
            .map(FluxHelper::new)
                // 使用 flux 中的元素自定义 map 中的 key 的生成规则
                .collectMap(FluxHelper::getI);

        StepVerifier.create(mapMono)
            .expectNextMatches(integerFluxHelperMap ->
                integerFluxHelperMap.size() == 2 &&
                    integerFluxHelperMap.get(0).getI() == 0 &&
                    integerFluxHelperMap.get(1).getI() == 1);
    }
}
