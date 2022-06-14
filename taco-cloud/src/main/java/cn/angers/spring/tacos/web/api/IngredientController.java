package cn.angers.spring.tacos.web.api;

import cn.angers.spring.tacos.data.IngredientRepository;
import cn.angers.spring.tacos.domain.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author : liuanglin
 * @date : 2022/6/13 15:49
 * @description :
 */
@RestController
@RequestMapping(path = "/api/ingredients",produces = "application/json")
public class IngredientController {

    @Autowired
    private IngredientRepository repository;

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
        return repository.save(ingredient);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") String ingredientId) {
        repository.deleteById(ingredientId);
    }
}
