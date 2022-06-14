package cn.angers.spring.tacos.data;

import cn.angers.spring.tacos.User;
import cn.angers.spring.tacos.domain.TacoOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author : liuanglin
 * @date : 2022/6/9 08:39
 * @description : 订单信息数据库接口
 *
 * CrudRepository 内置了很多方法
 * 应用启动时 Spring Data 会自动生成实现方法
 * 所以不需要在自定义实现类
 */
public interface OrderRepository extends CrudRepository<TacoOrder,Long> {

    /*
    spring 会解析 Repository接口中的方法
    通过方法的语义
    生成对应的实现
     */
    List<TacoOrder> findByDeliveryZip(String deliveryZip);

    List<TacoOrder> findByUserOrderByCreatedDateDesc(User user, Pageable pageable);
}
