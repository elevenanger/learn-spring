package cn.angers.spring.tacos.web;

import cn.angers.spring.tacos.data.IngredientRepository;
import cn.angers.spring.tacos.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author : liuanglin
 * @date : 2022/6/8 11:39
 * @description : 将页面的 ingredient 数据转换成 Ingredient 对象
 */
@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        /*
        使用数据库数据
        不需要在代码中硬编码数据写入map
         */
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);
    }
}
