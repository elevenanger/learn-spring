package cn.angers.spring.tacos.web.api;

import cn.angers.spring.tacos.User;
import cn.angers.spring.tacos.data.UserRepository;
import cn.angers.spring.tacos.messaging.KafkaUserMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author : liuanglin
 * @date : 2022/6/13 08:49
 * @description : 用户相关
 */
@RestController
@RequestMapping(path = "/api/user",produces = "application/json")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaUserMessagingService userMessagingService;

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody User user){
        userMessagingService.sendUser(user);
        return userRepository.save(user);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
        return userRepository.findByUsername(username);
    }
}
