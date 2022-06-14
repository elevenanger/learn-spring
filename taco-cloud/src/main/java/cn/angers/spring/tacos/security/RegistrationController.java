package cn.angers.spring.tacos.security;

import cn.angers.spring.tacos.User;
import cn.angers.spring.tacos.data.UserRepository;
import cn.angers.spring.tacos.messaging.KafkaUserMessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : liuanglin
 * @date : 2022/6/10 15:08
 * @description : 用户注册控制器
 */
@Controller
@RequestMapping("/register")
@Slf4j
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    KafkaUserMessagingService userMessagingService;

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    /**
     * 注册
     * @param form 表单数据
     * @return 重定向到登录页面
     */
    @PostMapping
    public String processRegistration(RegistrationForm form) {
        User user = form.toUser(passwordEncoder);
        log.info("注册用户 {} ", user);
        // 发送 kafka 消息
        userMessagingService.sendUser(user);
        userRepository.save(user);
        return "redirect:/login";
    }
}
