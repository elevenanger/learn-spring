package cn.angers.spring.tacos.web.api;

import cn.angers.spring.tacos.data.TacoRepository;
import cn.angers.spring.tacos.domain.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author : liuanglin
 * @date : 2022/6/11 19:35
 * @description : taco 相关 rest api
 * @RestController 注解声明 controller 中的所有方法都需要将返回值写入返回体
 */
@RestController
@RequestMapping(path = "/api/tacos",
    produces = "application/json")
@CrossOrigin("https://anger.cn:8443")
public class TacoController {
    @Autowired
    private TacoRepository tacoRepository;

    /**
     * 获取最近定制的 Taco 列表
     * @GetMapping(params = "recent") params 定义请求 api 的参数
     * @return taco 列表
     */
    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        PageRequest request = PageRequest.of(
            0,12, Sort.by("CreatedDate").descending());
        return tacoRepository.findAll(request).getContent();
    }

    /**
     * 根据 id 获取 taco
     * @GetMapping("/{id}") 是占位符
     * @PathVariable("id") 映射占位符，将 rest 请求客户端上送的 id 值赋值给方法中的参数
     * @param id taco id
     * @return taco
     */
    @GetMapping("/{id}")
    public ResponseEntity<Taco> getTacoById(@PathVariable("id") Long id) {
        Optional<Taco> taco = tacoRepository.findById(id);
        return
            taco
                // taco 有值则返回 taco 对象 http 状态码 200
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                // taco 无值则返回 404
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    /*
    @RequestBody 声明入参
    将 JSON 格式请求参数转换为参数对象
     */
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }
}
