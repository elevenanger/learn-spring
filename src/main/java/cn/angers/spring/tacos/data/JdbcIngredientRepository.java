package cn.angers.spring.tacos.data;

import cn.angers.spring.tacos.domain.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author : liuanglin
 * @date : 2022/6/8 17:03
 * @description :
 */
@Repository
public class JdbcIngredientRepository implements IngredientRepository{

    private JdbcTemplate jdbcTemplate;

    /*
    因为只有一个构造函数
    Spring 通过构造函数的参数
    隐式地添加自动注入依赖
    如果有多个构造函数
    则需要显式声明 @Autowired 注解
     */
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query(
            "select  id, name, type from ingredient",
            this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> ingredients = jdbcTemplate.query(
            "select  id, name, type from ingredient where id = ?",
            this::mapRowToIngredient,
            id);
        return ingredients.isEmpty()?
            Optional.empty():
            Optional.of(ingredients.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update(
            "insert into ingredient (id, name, type) values (?, ?, ?)",
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getType().toString()
        );
        return ingredient;
    }

    /**
     * 将数据库的结果集映射为对象
     * @param row 查询记录
     * @param rowNum 行数
     * @return 映射完成的 Ingredient 对象
     */
    private Ingredient mapRowToIngredient(ResultSet row,int rowNum)
        throws SQLException {
        return new Ingredient(
            row.getString("id"),
            row.getString("name"),
            Ingredient.Type.valueOf(row.getString("type"))
        );
    }
}
