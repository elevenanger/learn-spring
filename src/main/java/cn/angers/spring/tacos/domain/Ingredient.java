package cn.angers.spring.tacos.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author : liuanglin
 * @date : 2022/6/8 08:55
 * @description : 成分类
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
public class Ingredient {
    /*
    lombok 自动生成构造函数
    final 实例域作为构造函数的参数
     */
    @Id
    private final String id;
    private final String name;
    private final Type type;
    public enum Type {WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE}
}
