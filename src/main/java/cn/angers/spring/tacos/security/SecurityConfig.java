package cn.angers.spring.tacos.security;

import cn.angers.spring.tacos.User;
import cn.angers.spring.tacos.data.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Objects;

/**
 * @author : liuanglin
 * @date : 2022/6/9 17:14
 * @description : Spring Security 配置类
 */
@Configuration
public class SecurityConfig {

    /**
     * @Bean 注释的方法为工厂方法
     * 创建特定类的实例
     * 定义密码编码器工厂方法
     * @return 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 自定义 UserDetailsService 使用 JPA 接口获取用户信息
     * @param repository 持久化的用户信息
     * @return 自定义的 UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return username -> {
            User user = repository.findByUsername(username);
            if (Objects.nonNull(user)) return user;
            throw new UsernameNotFoundException(
                "用户 " + username + " 不存在"
            );
        };
    }

    /**
     * 通过定义 SecurityFilterChain 配置安全规则
     * @param httpSecurity
     * @return 自定义规则的 SecurityFilterChain 对象
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeRequests()
            // design 和 orders 需要 ROLE_USER 角色登录后才能访问
                .antMatchers("/design","/orders").hasRole("USER")
            /*
            所有请求都允许所有用户发起
            规则定义的顺序很重要
            先定义的规则的优先级要高于后定义的规则
            hasRole() 和 permitAll() 是声明请求限制的方法
             */
                .antMatchers("/","/**").permitAll()
                .antMatchers(HttpMethod.POST,"/admin/**")
                    .access("hasRole('ADMIN')")
            .and()
            // 自定义登录页面
                .formLogin()
                    .loginPage("/login")
                    // 登录成功后跳转的页面
                    .defaultSuccessUrl("/design",true)
            /*
            and() 方法表示已经完成一部分 http 配置
            并准备添加一些新的 http 配置
             */
            .and()
                .oauth2Login()
                    .loginPage("/login")
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
            .build();
    }
}
