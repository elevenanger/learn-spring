package cn.angers.spring.tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : liuanglin
 * @date : 2022/6/9 17:14
 * @description : Spring Security 配置类
 */
@Configuration
public class SecurityConfig {

    /**
     * 定义密码编码器
     * @return 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
