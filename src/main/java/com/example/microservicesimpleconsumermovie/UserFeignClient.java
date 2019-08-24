package com.example.microservicesimpleconsumermovie;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@FeignClient(name="microservice-provider-user", fallback= FeignClientFallback.class)
public interface UserFeignClient {
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);

    @RequestMapping(value="/get", method = RequestMethod.GET)
    public User get1(@RequestParam("id") Long id, @RequestParam("username") String username);

    @RequestMapping(value="/get", method = RequestMethod.GET)
    public User get2(@RequestParam Map<String, Object> map);

    @RequestMapping(value="/post", method = RequestMethod.POST)
    public User post(@RequestBody User user);
}

@Component
class FeignClientFallback implements UserFeignClient {

    @Override
    public User findById(Long id){
        User user = new User();
        user.setId(-1L);
        user.setUsername("默认用户");
        return user;
    }
    @Override
    public User get1(Long id, String username){
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }
    @Override
    public User get2(Map<String, Object> map){
        String id = map.get("id").toString();
        String username = map.get("username").toString();
        User user = new User();
        user.setId(Long.parseLong(id));
        user.setUsername(username);
        return user;
    }
    @Override
    public User post(User user){
        return user;
    }
}

