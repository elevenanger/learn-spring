package com.tacos.auth.user;

import org.springframework.data.repository.CrudRepository;

/**
 * @author : liuanglin
 * @date : 2022/6/10 08:42
 * @description :
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
