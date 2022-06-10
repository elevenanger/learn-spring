package cn.angers.spring.tacos.security;

import cn.angers.spring.tacos.data.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : liuanglin
 * @date : 2022/6/10 15:08
 * @description : 用户注册控制器
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    /**
     * 注册
     * @param form 表单数据
     * @return 重定向到登录页面
     */
    public String processRegistration(RegistrationForm form) {
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
