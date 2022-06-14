package tacos.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.RequestScope;
import tacos.client.IngredientService;
import tacos.client.RestIngredientService;

import java.util.UUID;

/**
 * @author : liuanglin
 * @date : 2022/6/14 09:12
 * @description :
 */
@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
        throws Exception {
        http.authorizeRequests(
            auth -> auth.anyRequest().authenticated())
            // oauth2 授权登录页面
            .oauth2Login(
                login ->
                    login.loginPage("/login/oauth2/authorization/taco-client"))
            .oauth2Client(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @RequestScope
    public IngredientService ingredientService(
        OAuth2AuthorizedClientService clientService) {
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        String accessToken = null;
        if (authentication.getClass()
            .isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken token =
                (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId =
                token.getAuthorizedClientRegistrationId();
            if (clientRegistrationId.equals("taco-client")) {
                OAuth2AuthorizedClient client
                    = clientService.loadAuthorizedClient(
                        clientRegistrationId,token.getName());
                accessToken = client.getAccessToken().getTokenValue();
            }
        }
        return new RestIngredientService(accessToken);
    }

    @Bean
    public ClientRegistrationRepository repository(PasswordEncoder encoder){
        ClientRegistration registration =
            ClientRegistration
                .withRegistrationId(UUID.randomUUID().toString())
                .clientId("taco-client")
                .clientSecret(encoder.encode("secrete"))
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
                .build();
        return new InMemoryClientRegistrationRepository(registration);
    }
}
