package tacos.client;

/**
 * @author : liuanglin
 * @date : 2022/6/14 10:21
 * @description :
 */
public interface IngredientService {
    Iterable<Ingredient> findAll();
    Ingredient addIngredient(Ingredient ingredient);
}
