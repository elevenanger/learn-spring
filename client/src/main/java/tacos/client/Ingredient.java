package tacos.client;

import lombok.*;

/**
 * @author : liuanglin
 * @date : 2022/6/8 08:55
 * @description : 成分类
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;
    public enum Type {WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE}
}
