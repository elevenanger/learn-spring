package cn.angers.spring.tacos.domain;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @author : liuanglin
 * @date : 2022/6/9 08:41
 * @description : 成分关联数据对象
 */
@Data
public class IngredientRef {
    private final String ingredient;
}
