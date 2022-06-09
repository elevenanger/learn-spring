package cn.angers.spring.tacos.controller;

import static cn.angers.spring.tacos.domain.Ingredient.Type;

import cn.angers.spring.tacos.data.IngredientRepository;
import cn.angers.spring.tacos.domain.Ingredient;
import cn.angers.spring.tacos.domain.Taco;
import cn.angers.spring.tacos.domain.TacoOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author : liuanglin
 * @date : 2022/6/8 09:13
 * @description : 编辑 taco controller
 */
@Slf4j
@Controller
/*
@RequestMapping 注解在类上
声明该类处理的请求种类 - /design 路径的请求
 */
@RequestMapping("/design")
/*
@SessionAttributes
表示在该类中放入 model 的 TacoOrder 对象需要维护在会话中
创建订单的第一步就是创建 taco
创建订单需要在会话中进行
因为在这个过程中会跨越多个请求
 */
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * 将各种浇头分类添加到视图模型中
     * @ModelAttribute 注解声明该方法
     * 方法在处理请求时会被调用
     * 构建成分列表
     * 将其添加到 model 中
     * @param model spring 视图模型
     *              Model 是负责在 controller 和视图之间传递数据的对象
     */
    @ModelAttribute
    public void addIngredientsToModel(Model model) {

        Iterable<Ingredient> ingredients = ingredientRepository.findAll();
        /*
        可选择的浇头清单
         */
//        List<Ingredient> ingredients = Arrays.asList(
//            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//            new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//            new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//            new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//            new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//            new Ingredient("CHED", "Cheddar", Type.CHEESE),
//            new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//            new Ingredient("SLSA", "Salsa", Type.SAUCE),
//            new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        );

        Type [] types = Type.values();
        /*
        按类型区分不同的浇头
        并添加到 model 属性中
         */
        Arrays.stream(types)
            .forEach(type -> model.addAttribute(
                type.toString().toLowerCase()
                ,filterByType(ingredients,type)));
    }

    /**
     * 订单对象
     * @ModelAttribute(name = "tacoOrder") 与 @SessionAttributes("tacoOrder") 关联
     * TacoOrder 对象保存用户跨多个请求创建订单的对象在会话中
     * @return TacoOrder 对象
     */
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    /**
     * Taco 对象在 model 中
     * 创建一个 new Taco() 对象
     * 以便视图相应 /design 请求时使用的非空对象
     * @return new Taco() 对象
     */
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        /*
        对应的视图模板
         */
        return "design";
    }

    private Iterable<Ingredient> filterByType(
        Iterable<Ingredient> ingredients, Type type) {
        return StreamSupport.stream(ingredients.spliterator(),false)
            .filter(ingredient -> ingredient.getType().equals(type))
            .collect(Collectors.toList());
    }

    /**
     * 将用户选择的 taco 添加到订单信息里面
     * taco 定义完成后
     * 重定向到订单配送页面填写配送信息
     * @param taco 用户创建的 taco 对应对象
     * @param tacoOrder taco 订单对象
     * @return 重定向视图
     */
    @PostMapping
    public String processTaco(
        /*
        @Valid 注解告诉 Spring MVC 在方法执行前对 taco 对象执行校验
        如果有任何错误信息
        这些信息将会在传递给 processTaco() 方法 的Error 对象中捕获
         */
        @Valid Taco taco,
        Errors errors,
        /*
        @ModelAttribute  表示这里的数据来自 model 中的 tacoOrder 对象
        */
        @ModelAttribute TacoOrder tacoOrder){
        /*
        spring validation 检查录入信息的准确性
        如果校验不通过存在错误信息则返回 taco 定制页面
         */
        if (errors.hasErrors()) return "design";
        tacoOrder.addTaco(taco);
        log.info("Processing taco : {} " , taco);
        /*
        返回值 redirect: 前缀表示这是一个重定向的视图
        当 processTaco() 方法执行完毕
        用户浏览器需要重定向到相对路径 /orders/current
         */
        return "redirect:/orders/current";
    }
}
