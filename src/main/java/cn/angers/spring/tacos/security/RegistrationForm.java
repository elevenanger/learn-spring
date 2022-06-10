package cn.angers.spring.tacos.security;

import cn.angers.spring.tacos.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : liuanglin
 * @date : 2022/6/10 15:10
 * @description : 注册表单数据
 */
@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String city;
    private String street;
    private String state;
    private String zip;
    private String phone;


    /**
     * 将表单数据转换成用户对象
     * @param encoder 密码编码器
     * @return 用户实例
     */
    public User toUser(PasswordEncoder encoder){
        return new User(username,encoder.encode(password),
            fullname,street,city,state,zip,phone);
    }
}
