package com.tacos.auth;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * @author : liuanglin
 * @date : 2022/6/13 18:43
 * @description : 授权服务器配置
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    /**
     * 设置 OAuth2 认证服务器的默认属性
     * @Order(Ordered.HIGHEST_PRECEDENCE) 注解声明如果还有其它的 Bean 声明这个类型
     * 这个 bean 的优先级最高
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authSecurityFilterChain(HttpSecurity http)
        throws Exception {
        OAuth2AuthorizationServerConfiguration
            .applyDefaultSecurity(http);
        return http
            // 使用默认的认证页面
            .formLogin(Customizer.withDefaults())
            .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(
        PasswordEncoder encoder) {
        RegisteredClient registeredClient =
            RegisteredClient.withId(UUID.randomUUID().toString())
                // clientID 授权访问的 client 标识
                .clientId("taco-client")
                // client secrete 客户端与认证服务器交互的参数
                .clientSecret(encoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // 客户端需要实现的授权类型
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                // 授权完成后的重定向地址
                .redirectUri(
                    "http://localhost:9090/login/oauth2/code/taco-client")
                // 客户端允许的访问范围
                .scope("writeIngredients")
                .scope("deleteIngredients")
                .scope(OidcScopes.OPENID)
                .clientSettings(
                    clientSettings -> clientSettings.requireUserConsent(true)
                )
                .build();
        return  new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        RSAKey rsaKey = generateRSA();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return ((jwkSelector, context) -> jwkSelector.select(jwkSet));
    }

    private static RSAKey generateRSA() throws NoSuchAlgorithmException {
        KeyPair keyPair = genKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    }

    private static KeyPair genKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * jwkSource 创建 RSA 密钥对用于对 token 签名
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
