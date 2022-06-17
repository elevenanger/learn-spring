package reactor;

import lombok.Data;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author : liuanglin
 * @date : 2022/6/16 14:42
 * @description : Flux 测试
 */
class CreateFluxTests {

    /**
     * 使用 Flux.create() 创建 Flux 对象
     */
    @Test
    void createAFluxWithInterface() {
        /*
        创建一个 Flux 类型 Reactive Stream publisher
         */
        Flux<String> nameJust =
            Flux.just("Kevin","Micheal","An");
        // middle operation1
        nameJust.map(String::toUpperCase);
        // subscriber 1
        nameJust.subscribe(name -> System.out.println("UPPER NAME : " + name));
        // middle operation 2
        nameJust.map(String::toLowerCase);
        // subscriber 2
        nameJust.subscribe(name -> System.out.println("lower name : " + name));
    }


    @Test
    void stepVerify() {
        Flux<String> nameJust =
            Flux.just("Kevin","Micheal","An");
        /*
        使用 StepVerify 校验 Flux
        StepVerify(Publisher) 订阅 publisher
        对流经的数据进行断言
        校验数据 stream 按照预期完成
         */
        StepVerifier.create(nameJust)
            .expectNext("Kevin")
            .expectNext("Micheal")
            .expectNext("An")
            .verifyComplete();
    }

    /**
     * 通过数组创建 Flux
     */
    @Test
    void createFluxFromArray() {
        String [] words = {"anger", "crazy", "humble"};
        Flux<String> wordsFlux = Flux.fromArray(words);
        StepVerifier.create(wordsFlux)
            .expectNext("anger")
            .expectNext("crazy")
            .expectNext("humble")
            .verifyComplete();
    }

    /**
     * 通过 Iterable 对象创建 Flux
     */
    @Test
    void createFluxFromIterable() {
        Iterable<Integer> integers =
            IntStream.range(0,3)
                .boxed()
                .collect(Collectors.toList());
        Flux<Integer> integerFlux = Flux.fromIterable(integers);
        StepVerifier.create(integerFlux)
            .expectNext(0)
            .expectNext(1)
            .expectNext(2)
            .verifyComplete();
    }

    /**
     * 使用 Stream 创建 Flux
     */
    @Test
    void createFluxFromStream() {
        Flux<Integer> integerFlux
            = Flux.fromStream(IntStream.range(0,3).boxed());
        StepVerifier.create(integerFlux)
            .expectNext(0)
            .expectNext(1)
            .expectNext(2)
            .verifyComplete();
    }

    /**
     * 使用 Flux.range() 创建 Flux
     */
    @Test
    void createFluxWithRange() {
        Flux<Integer> integerFlux = Flux.range(0,3);
        integerFlux
            .map(FluxHelper::new)
            .subscribe(Runnable::run);
        StepVerifier.create(integerFlux)
            .expectNext(0)
            .expectNext(1)
            .expectNext(2)
            .verifyComplete();
    }

    /**
     * 使用 Flux.interval() 创建 flux 对象
     */
    @Test
    void createFluxWithInterval() {
        Flux<Long> intervalFlux =
            // 时间间隔
            Flux.interval(Duration.ofSeconds(1))
                // 限制生成元素的数量
                .take(3);
        intervalFlux.map(FluxHelper::new).subscribe(Runnable::run);
        StepVerifier.create(intervalFlux)
            .expectNext(0L)
            .expectNext(1L)
            .expectNext(2L)
            .verifyComplete();
    }
}

@Data
class FluxHelper implements Runnable {
    private int i;
    private long j;

    public FluxHelper(int i) {
        this.i = i;
        System.out.println(i);
    }

    public FluxHelper(long j) {
        this.j = j;
        System.out.println(j);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}