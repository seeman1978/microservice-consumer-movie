package com.example.microservicesimpleconsumermovie;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Moviecontroller {
    @Autowired
    private UserFeignClient userFeignClient;

    //@HystrixCommand(fallbackMethod="findByIdFallback")
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id){
        return this.userFeignClient.findById(id);
    }

    @GetMapping("/user/get1")
    public User get1(User user){
        return this.userFeignClient.get1(user.getId(), user.getUsername());
    }

    @GetMapping("/user/get2")
    public User get2(User user){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        return this.userFeignClient.get2(map);
    }

    @GetMapping("/user/post")
    public User post(User user){
        return this.userFeignClient.post(user);
    }

    public User findByIdFallback(Long id){
        User user = new User();
        user.setId(-1L);
        user.setName("默认用户");
        return user;
    }
}
