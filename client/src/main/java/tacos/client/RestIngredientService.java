package tacos.client;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author : liuanglin
 * @date : 2022/6/14 10:23
 * @description :
 */
public class RestIngredientService implements IngredientService{

    private RestTemplate restTemplate;

    public RestIngredientService(String accessToken) {
        this.restTemplate = new RestTemplate();
        /*
        如果 token 不为空
        则是需要授权的请求
        增加 http 拦截器
        在 http header 中新增授权参数
         */
        if (accessToken != null) {
            this.restTemplate
                .getInterceptors()
                .add(getInterceptor(accessToken));
        }
    }

    /**
     * 将 AccessToken 添加包 http header 中的授权参数
     * @param accessToken token
     * @return http 拦截器
     */
    private ClientHttpRequestInterceptor getInterceptor(String accessToken){
        return (request, body, execution) ->  {
            request.getHeaders().add(
                "Authorization", "Bearer " + accessToken
            );
            return execution.execute(request,body);
        };
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return Arrays.asList(
            Objects.requireNonNull(restTemplate.getForObject(
                "http://localhost:8091/api/ingredients",
                Ingredient[].class)));
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return restTemplate.postForObject(
            "http://localhost:8091/api/ingredients",
            ingredient,
            Ingredient.class);
    }


}
