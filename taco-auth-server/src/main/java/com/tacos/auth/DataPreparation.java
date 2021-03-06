package com.tacos.auth;

import com.tacos.auth.user.User;
import com.tacos.auth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

/**
 * @author : liuanglin
 * @date : 2022/6/13 18:26
 * @description : 准备 auth-server 数据
 */
@Configuration
public class DataPreparation {

    @Autowired
    UserRepository userRepository;

    @Bean
    public ApplicationRunner dataLoader(PasswordEncoder encoder) {
        Random random = new Random(10);
        return args -> userRepository.save(
            new User("anger" + random.nextInt(1000), encoder.encode("123456"), "ROLE_ADMIN"));
    }
}
