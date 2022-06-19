package flux.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : liuanglin
 * @date : 2022/6/8 09:07
 * @description : taco 订单
 *
 * @Table 注解为 Table
 * Spring Data JDBC 配合实现了 Repository 的接口知道如何持久化该领域的数据
 * 该注解时可选的，在实现 Repository 的接口已经定义了域类
 * 表明将会根据类名驼峰命名的规则进行相应的转换
 * TacoOrder -> taco_order
 * 符合这个转换规则则可以不需要显示声明 name
 * 如果不符合此规则则需要 name 显式地声明表名
 *
 * 使用 Spring Data JPA 的注解
 * 在域类上注解@Entity 表示这是一个实体
 */
@Data
//@Table()
@Entity
public class TacoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdDate = new Date();
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco){
        tacos.add(taco);
    }

}
