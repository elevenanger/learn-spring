package cn.angers.spring.tacos.data;

import cn.angers.spring.tacos.User;
import cn.angers.spring.tacos.domain.Ingredient;
import cn.angers.spring.tacos.domain.Taco;
import cn.angers.spring.tacos.domain.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static cn.angers.spring.tacos.domain.Ingredient.Type;

/**
 * @author : liuanglin
 * @date : 2022/6/12 19:31
 * @description :
 */
@Configuration
public class DataPreparation {

    @Autowired
    TacoRepository tacoRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    OrderRepository repository;

    @Autowired
    UserRepository userRepository;

    @Bean
    public CommandLineRunner dataLoader() {
        return args -> {
            Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
            Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
            Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
            Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
            Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
            Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
            Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
            Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
            Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
            Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);

            ingredientRepository.save(flourTortilla);
            ingredientRepository.save(cornTortilla);
            ingredientRepository.save(groundBeef);
            ingredientRepository.save(carnitas);
            ingredientRepository.save(tomatoes);
            ingredientRepository.save(lettuce);
            ingredientRepository.save(cheddar);
            ingredientRepository.save(jack);
            ingredientRepository.save(salsa);
            ingredientRepository.save(sourCream);

            Taco taco1 = new Taco();
            taco1.setName("Super Taco No.1");
            taco1.setIngredients(Arrays.asList(flourTortilla,groundBeef,salsa,sourCream,cheddar,jack));
            Taco taco2 = new Taco();
            taco1.setName("Super Taco No.2");
            taco1.setIngredients(Arrays.asList(cornTortilla,groundBeef,salsa,sourCream,cheddar,jack));
            User user = userRepository.findByUsername("anger");
            TacoOrder tacoOrder = new TacoOrder();
            tacoOrder.setTacos(Arrays.asList(taco1,taco2));
            tacoOrder.setCcCVV("123");
            tacoOrder.setCcExpiration("202210");
            tacoOrder.setCcNumber("6222021121000611");
            tacoOrder.setDeliveryName("an");
            tacoOrder.setDeliveryCity("Dong Guan");
            tacoOrder.setDeliveryStreet("Hong Fu");
            tacoOrder.setDeliveryZip("523000");
            tacoOrder.setDeliveryState("88");
            tacoOrder.setUser(user);
//            repository.save(tacoOrder);
        };
    }
}
