package cn.angers.spring.tacos.data;

import cn.angers.spring.tacos.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

/**
 * @author : liuanglin
 * @date : 2022/6/8 17:00
 * @description : 定义 Ingredient 数据操作接口
 *
 * 使用 Spring JDBC
 * 继承 Repository 接口
 *
 */
public interface IngredientRepository extends CrudRepository<Ingredient,String> {}