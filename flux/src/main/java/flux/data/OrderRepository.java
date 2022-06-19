package flux.data;

import flux.domain.TacoOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author : liuanglin
 * @date : 2022/6/9 08:39
 * @description : 订单信息数据库接口
 *
 */
public interface OrderRepository extends CrudRepository<TacoOrder,Long> {

    List<TacoOrder> findByDeliveryZip(String deliveryZip);

}
