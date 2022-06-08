package cn.angers.spring.tacos.data;

import cn.angers.spring.tacos.domain.Ingredient;

import java.util.Optional;

/**
 * @author : liuanglin
 * @date : 2022/6/8 17:00
 * @description : 定义 Ingredient 数据操作接口
 */
public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
