package cn.angers.spring.tacos.domain;

import cn.angers.spring.tacos.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
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

    // @Id 注解表明这个域为 Taco_Order 表的 id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdDate = new Date();
    /*
    订单配送信息
     */
    @NotNull(message = "配送地址不能为空")
    private String deliveryName;
    @NotNull(message = "街道信息不能为空")
    private String deliveryStreet;
    @NotNull(message = "城市不能为空")
    private String deliveryCity;
    @NotNull(message = "洲不能为空")
    private String deliveryState;
    @NotNull(message = "请填写邮政编码")
    private String deliveryZip;
    /*
    订单付款信息
     */
    private String ccNumber;
    private String ccExpiration;
    @Digits(integer = 3,fraction = 0,message = "请填写正确的 CVV ")
    @Column(name = "cc_cvv")
    private String ccCVV;

    /*
    订购 taco 商品信息
    cascade = CascadeType.ALL 表示如果 order 被删除了
    相应的 taco 数据也应该被删除
     */
    @OneToMany(cascade = CascadeType.ALL,targetEntity = Taco.class)
    @JoinColumn(name = "taco_order")
    private List<Taco> tacos = new ArrayList<>();

    /**
     * 添加 taco
     * @param taco taco 对象
     */
    public void addTaco(Taco taco){
        tacos.add(taco);
    }

    /*
    使用 @ManyToOne 注解
    声明与另一个实体之间的关系
     */
    @ManyToOne
    private User user;
}
