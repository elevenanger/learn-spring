package com.tacos.auth.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

/**
 * @author : liuanglin
 * @date : 2022/6/10 08:36
 * @description : spring security 用户领域类
 * 实现 UserDetails 接口
 * 作为 spring security 的用户
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE,force = true)
@RequiredArgsConstructor
@Table(name = "auth_user")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String username;
    private final String password;
    private final String role;

    /**
     * 定义此类用户拥有的权限
     * @return  用户权限列表
     */
    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    @Transient
    public String getUsername() {
        return username;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}
