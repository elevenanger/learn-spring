package cn.angers.spring.tacos.domain;

import lombok.Data;

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
 */
@Data
public class TacoOrder implements Serializable {
    private static final long serialVersionUID = 1L;
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
    private String ccCVV;

    /*
    订购 taco 商品信息
     */
    private List<Taco> tacos = new ArrayList<>();

    /**
     * 添加 taco
     * @param taco taco 对象
     */
    public void addTaco(Taco taco){
        tacos.add(taco);
    }
}
