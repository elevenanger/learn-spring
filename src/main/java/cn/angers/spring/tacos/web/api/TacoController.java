package cn.angers.spring.tacos.web.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : liuanglin
 * @date : 2022/6/11 19:35
 * @description : taco 相关 rest api
 */
@RestController
@RequestMapping(path = "/api/tacos",
    produces = "application/json")
public class TacoController {

}
