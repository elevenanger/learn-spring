package com.tacos.auth;

import com.tacos.auth.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author : liuanglin
 * @date : 2022/6/13 17:52
 * @description :
 */
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity security) throws Exception {
        return
            security
                .authorizeRequests(request -> request.anyRequest().authenticated())
                .formLogin()
                .and().build();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository repository) {
        return repository::findByUsername;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
