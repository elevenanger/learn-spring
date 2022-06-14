package cn.angers.spring.tacos.data;

import cn.angers.spring.tacos.domain.Taco;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author : liuanglin
 * @date : 2022/6/12 19:09
 * @description :
 */
public interface TacoRepository extends PagingAndSortingRepository<Taco,Long> {
}
