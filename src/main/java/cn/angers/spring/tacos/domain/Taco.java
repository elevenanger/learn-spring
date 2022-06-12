package cn.angers.spring.tacos.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Size(min=3,message = "长度至少三位")
    private String name;

    @NotNull
    @ManyToMany
    @Size(min = 1,message = "至少选择一个浇头")
    private List<Ingredient> ingredients;
}
