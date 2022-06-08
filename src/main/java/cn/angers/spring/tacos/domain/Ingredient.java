package cn.angers.spring.tacos.domain;

import lombok.Data;

/**
 * @author : liuanglin
 * @date : 2022/6/8 08:55
 * @description : 成分类
 */
@Data
public class Ingredient {
    /*
    lombok 自动生成构造函数
    final 实例域作为构造函数的参数
     */
    private final String id;
    private final String name;
    private final Type type;
    public enum Type {WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE}
}
