package flux.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : liuanglin
 * @date : 2022/6/8 09:05
 * @description : taco 领域类
 */
@Data
@Entity
public class Taco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date createdDate = new Date();

    private String name;

    private List<Ingredient> ingredients;

}
